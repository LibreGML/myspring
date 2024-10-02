package com.jbj.jbjapi.controller.openApi;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.OpenDTO;
import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.param.OrderParam;
import com.jbj.jbjapi.domain.param.ProductDelParam;
import com.jbj.jbjapi.domain.param.ProductParam;
import com.jbj.jbjapi.domain.param.PurchaseParam;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.CategoryEntity;
import com.jbj.jbjapi.entity.ProductEntity;
import com.jbj.jbjapi.service.CategoryService;
import com.jbj.jbjapi.service.OrderService;
import com.jbj.jbjapi.service.ProductService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author mhw
 * @Version 1.0
 */
@Validated
@Api(value = "对外开发文档", tags = {"对外开发文档"})
@RequiredArgsConstructor
@RestController
@ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "header"),
        @ApiImplicitParam(name = "sign", value = "加密数据", required = true, dataType = "String", paramType = "header")
})
@RequestMapping("/open")
public class OpenApiController {

    @Resource
    private OrderService orderService;
    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "获取所有分类")
    @GetMapping("/all-category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "父id", required = false, dataType = "String"),
    })
    public R<List<CategoryEntity>> getAllCategory(@RequestParam(required = false) String id) {
        if (StringUtils.hasLength(id)) {
            return R.ok(categoryService.list(new LambdaQueryWrapper<CategoryEntity>().eq(CategoryEntity::getParentId, id)));
        }
        return R.ok(categoryService.list());
    }

    @ApiOperation(value = "获取所有商品")
    @GetMapping("/all-product")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = false, dataType = "String"),
    })
    public R<List<ProductParam>> getAllProduct(@RequestParam(required = false) String name, @RequestParam(required = false) String categoryId) {
        List<ProductEntity> list = productService.list(new LambdaQueryWrapper<ProductEntity>()
                .eq(ProductEntity::getStatus, "2")
                .like(StringUtils.hasLength(name), ProductEntity::getName, name)
                .eq(StringUtils.hasLength(categoryId), ProductEntity::getCategoryId, categoryId));
        List<ProductParam> productParams = BeanUtil.copyToList(list, ProductParam.class);
        return R.ok(productParams);
    }

    @ApiOperation(value = "获取商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = false, dataType = "String"),
    })
    @GetMapping("/product/detail")
    public R<ProductDelParam> getProductDetail(@RequestParam String id) {
        ProductEntity product = productService.getById(id);
        if (product == null) {
            throw new ServiceException("商品不存在");
        }
        productService.getPicDetails(product);
        ProductDelParam productParams = BeanUtil.copyProperties(product, ProductDelParam.class);
        productParams.setAttach(product.getAttachPam());
        productParams.setQuota(product.getInventory());
        return R.ok(productParams);
    }

    @ApiOperation(value = "购买商品")
    @OperateLog(tip = "购买商品")
    @PostMapping("/purchase")
    public R<PurchaseParam> purchase(@RequestBody PurchaseDTO purchase, HttpServletRequest request) {
        BusinessEntity business = (BusinessEntity) request.getAttribute("business");
        purchase.setUrl(request.getRemoteHost());
        return R.ok(orderService.purchase(purchase, business));
    }

    @ApiOperation(value = "订单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单号", required = false, dataType = "String"),
    })
    @PostMapping("/order")
    public R<List<OrderParam>> order(@RequestParam(required = false) String order, HttpServletRequest request) {
        BusinessEntity business = (BusinessEntity) request.getAttribute("business");
        return R.ok(orderService.order(order, business));
    }

}
