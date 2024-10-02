package com.jbj.jbjapi.controller.backstage;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.BusinessVo;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.SupplierService;
import com.jbj.jbjapi.service.SysBaseService;
import com.jbj.jbjapi.service.WithdrawalService;
import com.sun.org.apache.xpath.internal.operations.String;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "提现", tags = "提现")
@RestController
@RequestMapping("/withdrawal")
public class WithdrawalController {
    @Resource
    private WithdrawalService withdrawalService;

    @Resource
    private SysBaseService sysBaseService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<WithdrawalEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(withdrawalService.getById(key));
    }

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<WithdrawalEntity>> list(@RequestBody BusinessVo vo) {
        AccountEntity account = LoginHelper.getLoginUser();
        return R.ok(withdrawalService.page(vo.build(), new LambdaQueryWrapper<WithdrawalEntity>()
                .eq(!StringUtils.isEmpty(vo.getAccountId()), WithdrawalEntity::getUserId, vo.getAccountId())
                .eq(!account.getRoleType().equals("1"), WithdrawalEntity::getUserId, account.getId())
                .eq(StringUtils.hasLength(vo.getStatus()),WithdrawalEntity::getStatus,vo.getStatus())
                .orderByDesc(WithdrawalEntity::getCreateTime)
        ));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip = "提现数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody WithdrawalEntity entity) {
        AccountEntity account = LoginHelper.getLoginUser();
        entity.setUserId(account.getId());
        entity.setStatus("1");
        SysBaseEntity sysBase = sysBaseService.getById("withdrawal");
        JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
        if (account.getRoleType().equals("2")) {
            entity.setFree(jsonObject.getBigDecimal("busfree"));
        } else if (account.getRoleType().equals("3")) {
            entity.setFree(jsonObject.getBigDecimal("subfree"));
        }
        return R.ok(withdrawalService.save(entity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip = "提现数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody WithdrawalEntity entity) {
        return R.ok(withdrawalService.updateById(entity));
    }

    @ApiOperation(value = "提现数据处理")
    @OperateLog(tip = "提现数据处理")
    @PostMapping("/deal")
    public R<Boolean> deal(@RequestBody WithdrawalEntity entity) {
        WithdrawalEntity withdrawal = withdrawalService.getById(entity.getId());
        withdrawal.setStatus(entity.getStatus());
        withdrawal.setRemarks(entity.getRemarks());
        if (entity.getStatus().equals("3")) {
            BigDecimal jin = withdrawal.getFree().multiply(withdrawal.getWithdrawalAmount());
            withdrawal.setAccountAmount(withdrawal.getWithdrawalAmount().subtract(jin));
        }
        return R.ok(withdrawalService.updateById(withdrawal));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip = "提现数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(withdrawalService.removeById(key));
    }
}
