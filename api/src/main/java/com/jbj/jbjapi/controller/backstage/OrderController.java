package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.OrderVO;
import com.jbj.jbjapi.domain.Vo.SalesVO;
import com.jbj.jbjapi.domain.param.PurchaseParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.NoticeService;
import com.jbj.jbjapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "订单", tags = "订单")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @Resource
    private AccountService accountService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<OrderEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(orderService.getById(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<OrderEntity>> list(@RequestBody OrderVO vo, @RequestParam(required = false) String key) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (account.getRoleType().equals("1") && StringUtils.hasLength(key)) {
            account = accountService.getById(key);
        }
        Page<OrderEntity> list = orderService.page(vo.build(), new LambdaQueryWrapper<OrderEntity>()
                .eq(StringUtils.hasLength(vo.getOder()), OrderEntity::getOrderNumber, vo.getOder())
                .eq(account.getRoleType().equals("2"), OrderEntity::getMerchantId, account.getRoleId())
                .eq(account.getRoleType().equals("3"), OrderEntity::getSupplierId, account.getRoleId())
                .orderByDesc(OrderEntity::getOrderTime)
        );
        list.getRecords().forEach(res -> {
            AccountEntity accountEntity = accountService.getOne(new LambdaQueryWrapper<AccountEntity>()
                    .eq(AccountEntity::getRoleType, "2")
                    .eq(AccountEntity::getRoleId, res.getMerchantId())
            );
            if (accountEntity != null) {
                res.setMerchantId(accountEntity.getId());
            }
            AccountEntity accountEntity2 = accountService.getOne(new LambdaQueryWrapper<AccountEntity>()
                    .eq(AccountEntity::getRoleType, "3")
                    .eq(AccountEntity::getRoleId, res.getSupplierId())
            );
            if (accountEntity2 != null) {
                res.setSupplierId(accountEntity2.getId());
            }
        });
        return R.ok(list);
    }

    @ApiOperation(value = "申请售后")
    @OperateLog(tip = "订单数据申请售后")
    @PostMapping("/sales")
    public R<Boolean> sales(@RequestBody SalesVO salesVO) {
        return R.ok(orderService.sales(salesVO));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip = "订单数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody OrderEntity entity) {
        return R.ok(orderService.save(entity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip = "订单数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody OrderEntity entity) {
        return R.ok(orderService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip = "订单数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(orderService.removeById(key));
    }

    @ApiOperation(value = "商品补单")
    @OperateLog(tip = "商品补单")
    @PostMapping("/mentary")
    public R<Boolean> mentary(@RequestParam String key, HttpServletRequest request) {
        return R.ok(orderService.mentary(key, request));
    }
}
