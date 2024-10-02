package com.jbj.jbjapi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.AccountVo;
import com.jbj.jbjapi.domain.Vo.LoginVO;
import com.jbj.jbjapi.domain.Vo.UpdatePasswordVO;
import com.jbj.jbjapi.domain.param.AccountParam;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.SupplierEntity;
import com.jbj.jbjapi.entity.SysMenuEntity;
import com.jbj.jbjapi.entity.SysMenuRoleEntity;
import com.jbj.jbjapi.mapper.AccountMapper;
import com.jbj.jbjapi.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountEntity> implements AccountService {


    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private SysMenuRoleService sysMenuRoleService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private BusinessService businessService;

    @Override
    public LoginVO login(UserDTO account) {
        AccountEntity userEntity = this.getOne(new LambdaQueryWrapper<AccountEntity>().eq(AccountEntity::getAccount, account.getUserAccount()));
        if (!Objects.isNull(userEntity) && passwordEncoder.matches(account.getUserPassword(), userEntity.getPassword())) {
            if (userEntity.getRoleType().equals("3")) {
                SupplierEntity supplierEntity = supplierService.getById(userEntity.getRoleId());
                if (supplierEntity.getExamine() != null) {
                    switch (supplierEntity.getExamine()) {
                        case 1:
                            throw new ServiceException("账户待审核");
                        case 3:
                            throw new ServiceException("账户待审核失败");
                        default:
                    }
                }
            }
            userEntity.setPassword(null);
            return createUser(userEntity, "web");
        }
        throw new ServiceException("账户或密码错误");
    }

    @Override
    public LoginVO register(UserDTO account) {
        AccountEntity userEntity = this.getOne(new LambdaQueryWrapper<AccountEntity>().eq(AccountEntity::getAccount, account.getUserAccount()));
        if (!Objects.isNull(userEntity)) {
            throw new ServiceException("账户名称已被注册");
        }
        userEntity = new AccountEntity();
        userEntity.setPassword(passwordEncoder.encode(account.getUserPassword()));
        userEntity.setAccount(account.getUserAccount());
        userEntity.setRoleType(account.getRoleType());
        userEntity.setRoleId(account.getRoleId());
        userEntity.setHeadPic(account.getHeadPic());
        this.save(userEntity);
        return createUser(userEntity, "web");
    }

    @Override
    public Object authPermList() {
        AccountEntity account = LoginHelper.getLoginUser();
        List<SysMenuRoleEntity> list = sysMenuRoleService.list(new LambdaQueryWrapper<SysMenuRoleEntity>().eq(SysMenuRoleEntity::getRoleId, account.getRoleType()));
        List<String> menuIds = list.stream().map(SysMenuRoleEntity::getMenuId).collect(Collectors.toList());
        List<SysMenuEntity> sysMenus = sysMenuService.list(new LambdaQueryWrapper<SysMenuEntity>().in(SysMenuEntity::getMenuId, menuIds));
        // 根据 menuId 构建父子关系的映射
        Map<String, SysMenuEntity> menuMap = sysMenus.stream()
                .collect(Collectors.toMap(SysMenuEntity::getMenuId, menu -> menu));
        // 构建父子关系的菜单列表
        List<SysMenuEntity> parentMenus = sysMenus.stream()
                .filter(menu -> menu.getParentId().equals("1")) // 假设顶级菜单的 parentId 为 "1"
                .sorted(Comparator.comparingInt(SysMenuEntity::getSort)) // 根据 sort 进行排序
                .map(menu -> {
                    SysMenuEntity parentMenu = menuMap.get(menu.getMenuId());
                    if (parentMenu != null) {
                        parentMenu.setListMenus(getChildMenus(menu.getMenuId(), menuMap));
                    }
                    return parentMenu;
                })
                .collect(Collectors.toList());
        return parentMenus;
    }

    @Override
    public Page<AccountEntity> accountList(AccountVo accountVo) {
        Page<AccountEntity> page = this.baseMapper.accountList(accountVo.build(), accountVo);
        page.getRecords().forEach(res -> {
            res.setPassword(null);
        });
        return page;
    }

    @Override
    public Boolean updatePassword(UpdatePasswordVO updatePasswordVO) {
        AccountEntity account = LoginHelper.getLoginUser();
        AccountEntity userEntity = this.getById(account.getId());
        if (!Objects.isNull(userEntity) && passwordEncoder.matches(updatePasswordVO.getOldPassword(), userEntity.getPassword())) {
            AccountEntity user = new AccountEntity();
            user.setId(userEntity.getId());
            user.setPassword(passwordEncoder.encode(updatePasswordVO.getNewPassword()));
            this.updateById(user);
            return true;
        }
        throw new ServiceException("旧密码错误");
    }

    @Override
    public Boolean updateByPrimaryKey(AccountEntity accountEntity) {
        AccountEntity account = this.getById(accountEntity.getId());
        account.setAccount(accountEntity.getAccount());
        account.setHeadPic(accountEntity.getHeadPic());
        if (StringUtils.hasLength(accountEntity.getPassword())) {
            account.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        }
        return this.updateById(account);
    }

    @Override
    public AccountParam getDetail(String key) {
        AccountParam accountParam = new AccountParam();
        AccountEntity accountEntity = this.getById(key);
        accountParam.setAccount(accountEntity);
        if (accountEntity.getRoleType().equals("2")) {
            accountParam.setBusiness(businessService.getById(accountEntity.getRoleId()));
        }
        if (accountEntity.getRoleType().equals("3")) {
            accountParam.setSupplier(supplierService.getById(accountEntity.getRoleId()));
        }
        return accountParam;
    }

    // 递归获取子菜单
    private List<SysMenuEntity> getChildMenus(String parentId, Map<String, SysMenuEntity> menuMap) {
        return menuMap.values().stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .sorted(Comparator.comparingInt(SysMenuEntity::getSort))
                .map(menu -> {
                    menu.setListMenus(getChildMenus(menu.getMenuId(), menuMap));
                    return menu;
                })
                .collect(Collectors.toList());
    }

    private LoginVO createUser(AccountEntity userEntity, String device) {
        StpUtil.logout(userEntity.getId(), device);
        String token = createToken(userEntity.getId());
//        redisTemplate.opsForValue().set("wx_session_key:" + userEntity.getId(), token, 7200, TimeUnit.SECONDS);
        LoginHelper.login(userEntity, token, device);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(StpUtil.getTokenValue());
        loginVO.setUser(userEntity);
        return loginVO;
    }

    /**
     * 根据openId创建token
     *
     * @param id id
     * @return 返回token
     */
    private String createToken(String id) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("uid", id);
        map.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
        return JWTUtil.createToken(map, "web".getBytes());
    }
}
