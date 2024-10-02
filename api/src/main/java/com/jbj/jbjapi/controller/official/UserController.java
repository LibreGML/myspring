package com.jbj.jbjapi.controller.official;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.RegisterSupplierVO;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.*;
import com.jbj.jbjapi.source.kaka.KakaService;
import com.jbj.jbjapi.source.rainbow.CaihongService;
import com.jbj.jbjapi.source.storage.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Api(value = "用户", tags = "用户")
@RestController
@RequestMapping("/official/account")
public class UserController {

    @Resource
    private StorageService storageService;

    @Resource
    private CaihongService caihongService;

    @Resource
    private KakaService kakaService;

    @Resource
    private AccountService accountService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private BusinessService businessService;

    @Resource
    private SysSourceService sysSourceService;

    @Resource
    private SourceService sourceService;


    @ApiOperation(value = "货源列表")
    @GetMapping("/all-source")
    public R<List<SysSourceEntity>> getAllSource() {
      return R.ok(sysSourceService.list());
    }

    @ApiOperation(value = "连通性检测")
    @PostMapping("/connectivity")
    public R<Boolean> connectivity(@RequestBody SourceEntity entity, HttpServletRequest request) {
        String url = request.getRemoteHost();
        entity.setUrl(url);
        switch (entity.getSourceSystem()) {
            case "1":
                return R.ok(storageService.connectivity(entity));
            case "2":
                return R.ok(caihongService.connectivity(entity));
            case "3":
                return R.ok(kakaService.connectivity(entity));
            default:
                return R.ok(false);
        }
    }

    @ApiOperation(value = "供应商注册")
    @PostMapping("/register/supplier")
    public R<Boolean> registerSupplier(@RequestBody RegisterSupplierVO vo, HttpServletRequest request) {
        SourceEntity entity = BeanUtil.copyProperties(vo, SourceEntity.class);
//        if (!connectivity(entity, request).getData()) {
//            throw new ServiceException("连通性检测失败！");
//        }
        SupplierEntity supplier = BeanUtil.copyProperties(vo, SupplierEntity.class);
        AccountEntity userEntity = accountService.getOne(new LambdaQueryWrapper<AccountEntity>().eq(AccountEntity::getAccount, supplier.getAccount()));
        if (!Objects.isNull(userEntity)) {
            throw new ServiceException("账户名称已被注册");
        }
        supplierService.save(supplier);
        entity.setConnectivity("1");
        entity.setSupplierId(supplier.getId());
        sourceService.save(entity);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserAccount(supplier.getAccount());
        userDTO.setUserPassword(vo.getPassword());
        userDTO.setRoleType("3");
        userDTO.setHeadPic(supplier.getHeadPic());
        userDTO.setRoleId(supplier.getId());
        accountService.register(userDTO);
        return R.ok("注册成功");
    }

    @ApiOperation(value = "商家注册")
    @PostMapping("/register/business")
    public R<Boolean> registerBusiness(@RequestBody BusinessEntity entity) {
        AccountEntity userEntity = accountService.getOne(new LambdaQueryWrapper<AccountEntity>().eq(AccountEntity::getAccount, entity.getAccount()));
        if (!Objects.isNull(userEntity)) {
            throw new ServiceException("账户名称已被注册");
        }
        entity.setAbutmentKey(UUID.randomUUID().toString());
        businessService.save(entity);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserAccount(entity.getAccount());
        userDTO.setUserPassword(entity.getPassword());
        userDTO.setRoleType("2");
        userDTO.setHeadPic(entity.getHeadPic());
        userDTO.setRoleId(entity.getId());
        accountService.register(userDTO);
        return R.ok("注册成功");
    }
}
