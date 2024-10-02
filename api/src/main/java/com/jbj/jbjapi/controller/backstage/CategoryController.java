package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.CategoryVO;
import com.jbj.jbjapi.entity.AftersalesEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.CategoryEntity;
import com.jbj.jbjapi.entity.ProductEntity;
import com.jbj.jbjapi.service.BusinessService;
import com.jbj.jbjapi.service.CategoryService;
import com.jbj.jbjapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "商品分类", tags = "商品分类")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;
    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<CategoryEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(categoryService.getById(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<CategoryEntity>> list(@RequestBody CategoryVO vo) {
        Page<CategoryEntity> page = new Page<>();
        if (StringUtils.isEmpty(vo.getParentId())) {
             page = categoryService.page(vo.build(), new LambdaQueryWrapper<CategoryEntity>()
                    .isNull(CategoryEntity::getParentId).or(e -> e.eq(CategoryEntity::getParentId, ""))
                    .orderByAsc(CategoryEntity::getSort)
                    .orderByDesc(CategoryEntity::getCreateTime)
            );
        }else {
            List<CategoryEntity> list = categoryService.list(new LambdaQueryWrapper<CategoryEntity>()
                    .eq(CategoryEntity::getParentId, vo.getParentId())
                    .orderByAsc(CategoryEntity::getSort)
                    .orderByDesc(CategoryEntity::getCreateTime)
            );
            page.setRecords(list);
        }
        page.getRecords().forEach(res->{
            res.setNum(productService.count(new LambdaQueryWrapper<ProductEntity>()
                    .eq(ProductEntity::getCategoryId,res.getId())
            ));
        });
        return R.ok(page);
    }


    @ApiOperation(value = "获取所有分类")
    @GetMapping("/all")
    public R<List<CategoryEntity>> getAll(@RequestParam String name) {
        return R.ok(categoryService.getAll(name));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip = "分类数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody CategoryEntity entity) {
        return R.ok(categoryService.save(entity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip = "分类数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody CategoryEntity entity) {
        return R.ok(categoryService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip = "分类数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(categoryService.removeById(key));
    }
}
