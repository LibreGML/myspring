package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.PageQuery;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.AftersalesVO;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.AftersalesEntity;
import com.jbj.jbjapi.entity.OrderEntity;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.AftersalesService;
import com.jbj.jbjapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "售后", tags = "售后")
@RestController
@RequestMapping("/aftersales")
public class AftersalesController {
    @Resource
    private AftersalesService aftersalesService;

    @Resource
    private OrderService orderService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<AftersalesEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(aftersalesService.getOne(new LambdaQueryWrapper<AftersalesEntity>().eq(AftersalesEntity::getOrderId, key)));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<AftersalesEntity>> list(@RequestBody AftersalesVO vo) {
        AccountEntity account = LoginHelper.getLoginUser();
        List<String> list = new ArrayList<>();
        if (StringUtils.hasLength(vo.getOrder())|| !account.getRoleType().equals("1")) {
            List<OrderEntity> tmp = orderService.list(new LambdaQueryWrapper<OrderEntity>()
                    .like(OrderEntity::getOrderNumber, vo.getOrder())
                    .eq(account.getRoleType().equals("2"),OrderEntity::getMerchantId, account.getRoleId())
                    .eq(account.getRoleType().equals("3"),OrderEntity::getSupplierId, account.getRoleId())
            );
            tmp.forEach(res -> {
                list.add(res.getId());
            });
            if(list.size()==0){
                return R.ok(new Page<AftersalesEntity>());
            }
        }
        Page<AftersalesEntity> page = aftersalesService.page(vo.build(), new LambdaQueryWrapper<AftersalesEntity>()
                .in(list.size() > 0, AftersalesEntity::getOrderId, list)
                .orderByDesc(AftersalesEntity::getCreateTime)
        );
        page.getRecords().forEach(res -> {
            OrderEntity order = orderService.getById(res.getOrderId());
            if(order != null){
                res.setOrder(order.getOrderNumber());
            }
        });
        return R.ok(page);
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip="售后数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody AftersalesEntity entity) {
        return R.ok(aftersalesService.save(entity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip="售后数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody AftersalesEntity entity) {
        return R.ok(aftersalesService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip="售后数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(aftersalesService.removeById(key));
    }
}
