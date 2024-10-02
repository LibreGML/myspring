package com.jbj.jbjapi.controller.backstage;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.PageQuery;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.AccountVo;
import com.jbj.jbjapi.domain.Vo.LoginVO;
import com.jbj.jbjapi.domain.Vo.UpdatePasswordVO;
import com.jbj.jbjapi.domain.param.AccountParam;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.SupplierEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.BusinessService;
import com.jbj.jbjapi.service.SupplierService;
import com.jbj.jbjapi.service.SysBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "账号", tags = "账号")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @Resource
    private SysBaseService sysBaseService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private BusinessService businessService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<AccountParam> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(accountService.getDetail(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<AccountEntity>> list(@RequestBody AccountVo accountVo) {
        return R.ok(accountService.accountList(accountVo));
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @OperateLog(tip="账号数据新增")
    public R<Boolean> add(@RequestBody AccountEntity accountEntity) {
        return R.ok(accountService.save(accountEntity));
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @OperateLog(tip="账号数据更新")
    public R<Boolean> updateByPrimaryKey(@RequestBody AccountEntity accountEntity) {
        return R.ok(accountService.updateByPrimaryKey(accountEntity));
    }

    @ApiOperation(value = "删除")
    @PostMapping("/del")
    @OperateLog(tip="账号数据删除")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(accountService.removeById(key));
    }


    /**
     * 登录
     */
    @ApiOperation("登录")
    @OperateLog(tip="账号登录")
    @PostMapping("/login")
    public R<LoginVO> login(@RequestBody UserDTO account) {
        return R.ok(accountService.login(account));
    }

    @ApiOperation("注册")
    @OperateLog(tip="账号注册")
    @PostMapping("/register")
    public R<LoginVO> register(@RequestBody UserDTO account) {
        return R.ok(accountService.register(account));
    }

    @ApiOperation("修改密码")
    @OperateLog(tip="修改密码")
    @PostMapping("/update-password")
    public R<Boolean> updatePassword(@RequestBody UpdatePasswordVO updatePasswordVO) {
        return R.ok(accountService.updatePassword(updatePasswordVO));
    }

    @ApiOperation("获取权限")
    @GetMapping("/authPermList")
    public R<Object> authPermList() {
        return R.ok(accountService.authPermList());
    }

    @ApiOperation("获取权限")
    @GetMapping("/getUserInfo")
    public R<Object> getUserInfo() {
        Map<String, Object> map = new HashMap<>();
        AccountEntity account = LoginHelper.getLoginUser();
        map.put("account", account);
        if (account.getRoleType().equals("2")) {
            BusinessEntity business = businessService.getById(account.getRoleId());
            business.setWebsite(sysBaseService.getById("wallet").getFieldValue());
            map.put("business", business);
        }
        if (account.getRoleType().equals("3")) {
            SupplierEntity supplier = supplierService.getById(account.getRoleId());
            map.put("supplier", supplier);
        }
        return R.ok(map);
    }

    @ApiOperation("获取可提现数据")
    @GetMapping("/withdrawal")
    public R<Object> withdrawal() {
        Map<String, Object> map = new HashMap<>();
        AccountEntity account = LoginHelper.getLoginUser();
        SysBaseEntity sysBase = sysBaseService.getById("withdrawal");
        JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
        if (account.getRoleType().equals("2")) {
            BusinessEntity business = businessService.getById(account.getRoleId());
            map.put("balance", business.getWallet());
            map.put("free",jsonObject.getBigDecimal("busfree"));
        }
        if (account.getRoleType().equals("3")) {
            SupplierEntity supplier = supplierService.getById(account.getRoleId());
            map.put("balance", supplier.getBalance());
            map.put("free",jsonObject.getBigDecimal("subfree"));
        }
        return R.ok(map);
    }
}
