package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.ProductVO;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.CategoryService;
import com.jbj.jbjapi.service.OrderService;
import com.jbj.jbjapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "商品", tags = "商品")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private OrderService orderService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<ProductEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(productService.getById(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<ProductEntity>> list(@RequestBody ProductVO vo) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (account == null || account.getRoleType().equals("2")) {
            throw new ServiceException("无权限");
        }
        Page<ProductEntity> page = productService.page(vo.build(), new LambdaQueryWrapper<ProductEntity>()
                .like(StringUtils.hasLength(vo.getName()), ProductEntity::getName, vo.getName())
                .eq(StringUtils.hasLength(vo.getExamine()), ProductEntity::getStatus, vo.getExamine())
                .eq(StringUtils.hasLength(vo.getCategory()), ProductEntity::getCategoryId, vo.getCategory())
                .eq(account.getRoleType().equals("3"), ProductEntity::getSupplierId, account.getRoleId())
                .like(StringUtils.hasLength(vo.getId()), ProductEntity::getId, vo.getId())
                .orderByDesc(ProductEntity::getCreateTime)
        );
        orderService.getAllDea(page.getRecords());
        page.getRecords().forEach(res -> {
            CategoryEntity category = categoryService.getById(res.getCategoryId());
            if (category != null) {
                res.setCategoryName(category.getName());
            }
        });
        return R.ok(page);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @OperateLog(tip = "商品数据新增")
    public R<Boolean> add(@RequestBody ProductEntity entity) {
        return R.ok(productService.save(entity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip = "商品数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody Map<String, Object> vo, HttpServletRequest request) {
        return R.ok(productService.updateByPrimaryKey(vo, request));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip = "商品数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestBody Map<String, Object> vo) {
        List<Map<String, String>> selectList = (List<Map<String, String>>) vo.get("selectList");
        selectList.forEach(res -> {
            productService.removeById(res.get("id"));
        });
        return R.ok(true);
    }

    @ApiOperation(value = "更改分类")
    @PostMapping("/edit-fl")
    public R<Boolean> editFl(@RequestBody Map<String, Object> vo) {
        return R.ok(productService.editFl(vo));
    }
}
