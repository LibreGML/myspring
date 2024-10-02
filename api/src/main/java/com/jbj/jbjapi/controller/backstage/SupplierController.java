package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.BusinessVo;
import com.jbj.jbjapi.domain.Vo.SupplierVo;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.BusinessService;
import com.jbj.jbjapi.service.ProductService;
import com.jbj.jbjapi.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "供应商", tags = "供应商")
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Value("${default.password}")
    private String defaultPassword;
    @Resource
    private SupplierService supplierService;
    @Resource
    private AccountService accountService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<SupplierEntity> selectByPrimaryKey(@RequestParam String key) {
        SupplierEntity supplier = supplierService.getById(key);
        AccountEntity account = accountService.getOne(new LambdaQueryWrapper<AccountEntity>()
                .eq(AccountEntity::getRoleType,3).eq(AccountEntity::getRoleId,supplier.getId()).last("LIMIT 1"));
        supplier.setAccountId(account.getId());
        supplier.setAccount(account.getAccount());
        return R.ok(supplier);
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<SupplierEntity>> list(@RequestBody SupplierVo vo) {
        return R.ok(supplierService.supplierList(vo));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip="供应商数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SupplierEntity entity) {
        entity.setExamine(2);
        entity.setExamineReason("站长添加");
        AccountEntity userEntity = accountService.getOne(new LambdaQueryWrapper<AccountEntity>().eq(AccountEntity::getAccount, entity.getAccount()));
        if (!Objects.isNull(userEntity)) {
            throw new ServiceException("账户名称已被注册");
        }
        supplierService.save(entity);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserAccount(entity.getAccount());
        userDTO.setUserPassword(defaultPassword);
        userDTO.setRoleType("3");
        userDTO.setHeadPic(entity.getHeadPic());
        userDTO.setRoleId(entity.getId());
        accountService.register(userDTO);
        return R.ok();
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip="供应商数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody SupplierEntity entity) {
        return R.ok(supplierService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip="供应商数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        accountService.remove(new LambdaQueryWrapper<AccountEntity>()
                .eq(AccountEntity::getRoleType, 3).eq(AccountEntity::getRoleId, key));
        return R.ok(supplierService.removeById(key));
    }
}
