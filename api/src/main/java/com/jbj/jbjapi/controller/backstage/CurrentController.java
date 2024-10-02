package com.jbj.jbjapi.controller.backstage;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.FundVo;
import com.jbj.jbjapi.domain.param.StatisticsParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-22
 */
@RestController
@Api(value = "通用接口", tags = "通用接口")
@RequestMapping("/current")
public class CurrentController {

    @Resource
    private MemberOrderService memberOrderService;

    @Resource
    private OrderService orderService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private BusinessService businessService;

    @Resource
    private WithdrawalService withdrawalService;

    @ApiOperation(value = "头部统计")
    @GetMapping("/getAll")
    public R<StatisticsParam> getAll() {
        AccountEntity account = LoginHelper.getLoginUser();
        StatisticsParam statisticsParam = new StatisticsParam();
        statisticsParam.setAllTransaction(orderService.getAllTransaction(account));
        if (account.getRoleType().equals("1")) {
            List<SupplierEntity> list = supplierService.list(new LambdaQueryWrapper<SupplierEntity>().select(SupplierEntity::getBalance));
            Double sum = list.stream().map(SupplierEntity::getBalance).mapToDouble(BigDecimal::doubleValue).sum();
            statisticsParam.setAllBalance(sum);
            statisticsParam.setBusiness(businessService.count());
            statisticsParam.setSupplier(supplierService.count());
            statisticsParam.setTodayOder(orderService.count(new LambdaQueryWrapper<OrderEntity>().likeRight(OrderEntity::getOrderTime, LocalDate.now())));
            statisticsParam.setAllOder(orderService.count());
            statisticsParam.setAddBusiness(businessService.count(new LambdaQueryWrapper<BusinessEntity>().likeRight(BusinessEntity::getCreateTime, LocalDate.now())));
            statisticsParam.setAddSupplier(supplierService.count(new LambdaQueryWrapper<SupplierEntity>().likeRight(SupplierEntity::getCreateTime, LocalDate.now())));
            List<MemberOrderEntity> lists = memberOrderService.list(new LambdaQueryWrapper<MemberOrderEntity>()
                    .likeRight(MemberOrderEntity::getOrderTime, LocalDate.now())
                    .eq(MemberOrderEntity::getStatus,"2")
                    .notIn(MemberOrderEntity::getMemberName,"额度包购买")
            );
            long countOfMerchantIds = lists.stream().map(MemberOrderEntity::getMerchantId)
                    .distinct()
                    .count();
            statisticsParam.setNumberNum(countOfMerchantIds);
            List<MemberOrderEntity> allList = memberOrderService.list(new LambdaQueryWrapper<MemberOrderEntity>()
                    .likeRight(MemberOrderEntity::getOrderTime, LocalDate.now())
                    .eq(MemberOrderEntity::getStatus,"2")
            );
            BigDecimal allPic = allList.stream().map(MemberOrderEntity::getPaymentAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            statisticsParam.setProfit(allPic);
            List<MemberOrderEntity> allZh = memberOrderService.list(new LambdaQueryWrapper<MemberOrderEntity>()
                    .eq(MemberOrderEntity::getStatus,"2")
            );
            BigDecimal allPicZh = allZh.stream().map(MemberOrderEntity::getPaymentAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            statisticsParam.setAllProfit(allPicZh);
            List<WithdrawalEntity> entityList = withdrawalService.list(new LambdaQueryWrapper<WithdrawalEntity>().eq(WithdrawalEntity::getStatus,"3"));
            BigDecimal allWithdrawal = entityList.stream().map(WithdrawalEntity::getWithdrawalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            statisticsParam.setAllWithdrawal(allWithdrawal);
        } else if (account.getRoleType().equals("2")) {
            statisticsParam.setTodayOder(orderService.count(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getMerchantId, account.getRoleId()).likeRight(OrderEntity::getOrderTime, LocalDate.now())));
            statisticsParam.setAllOder(orderService.count(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getMerchantId, account.getRoleId())));
            List<WithdrawalEntity> entityList = withdrawalService.list(new LambdaQueryWrapper<WithdrawalEntity>()
                    .eq(WithdrawalEntity::getUserId,account.getId())
                    .eq(WithdrawalEntity::getStatus,"3"));
            BigDecimal allWithdrawal = entityList.stream().map(WithdrawalEntity::getWithdrawalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            statisticsParam.setAllWithdrawal(allWithdrawal);
        } else if (account.getRoleType().equals("3")) {
            statisticsParam.setTodayOder(orderService.count(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getSupplierId, account.getRoleId()).likeRight(OrderEntity::getOrderTime, LocalDate.now())));
            statisticsParam.setAllOder(orderService.count(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getSupplierId, account.getRoleId())));
            List<WithdrawalEntity> entityList = withdrawalService.list(new LambdaQueryWrapper<WithdrawalEntity>()
                    .eq(WithdrawalEntity::getUserId,account.getId())
                    .eq(WithdrawalEntity::getStatus,"3"));
            BigDecimal allWithdrawal = entityList.stream().map(WithdrawalEntity::getWithdrawalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            statisticsParam.setAllWithdrawal(allWithdrawal);
        }
        return R.ok(statisticsParam);
    }

    @ApiOperation(value = "7天订单走势")
    @GetMapping("/order-7")
    public R<Object> order7() {
        Map<String, Object> map = new HashMap<>();
        LocalDate today = LocalDate.now();
        List<String> days = new ArrayList<>();
        List<Integer> series = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            days.add(today.minusDays(i).format(DateTimeFormatter.ofPattern("MM月dd日")));
            AccountEntity account = LoginHelper.getLoginUser();
            if (account.getRoleType().equals("1")) {
                series.add(orderService.count(new LambdaQueryWrapper<OrderEntity>().likeRight(OrderEntity::getOrderTime, today.minusDays(i))));
            } else if (account.getRoleType().equals("2")) {
                series.add(orderService.count(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getMerchantId, account.getRoleId()).likeRight(OrderEntity::getOrderTime, today.minusDays(i))));
            } else if (account.getRoleType().equals("3")) {
                series.add(orderService.count(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getSupplierId, account.getRoleId()).likeRight(OrderEntity::getOrderTime, today.minusDays(i))));
            }
        }
        map.put("data", days);
        map.put("series", series);
        return R.ok(map);
    }

    @ApiOperation(value = "今日交易top5")
    @GetMapping("/transaction-5")
    public R<Object> transaction5() {
        Map<String, Object> map = new HashMap<>();
        List<String> days = new ArrayList<>();
        List<String> series = new ArrayList<>();
        List<Map<String, String>> list = orderService.getTransactionData();
        list.forEach(res -> {
            days.add(res.get("supplierName"));
            series.add(res.get("amount"));
        });
        map.put("data", days);
        map.put("series", series);
        return R.ok(map);
    }

    @ApiOperation(value = "今日交易top5")
    @GetMapping("/bus/transaction-5")
    public R<Object> busTransaction5() {
        Map<String, Object> map = new HashMap<>();
        List<String> days = new ArrayList<>();
        List<String> series = new ArrayList<>();
        List<Map<String, String>> list = orderService.getBusTransactionData();
        list.forEach(res -> {
            days.add(res.get("busName"));
            series.add(res.get("amount"));
        });
        map.put("data", days);
        map.put("series", series);
        return R.ok(map);
    }

    @ApiOperation(value = "今日分类交易top7")
    @GetMapping("/category-7")
    public R<Object> category7() {
        AccountEntity account = LoginHelper.getLoginUser();
        return R.ok(orderService.category7(account.getRoleType(),account.getRoleId()));
    }

}
