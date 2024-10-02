package com.jbj.jbjapi.controller.official;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.ProductVO;
import com.jbj.jbjapi.domain.Vo.SourceVo;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "货源模块", tags = "货源模块")
@RestController
@RequestMapping("/official/source")
public class OfficialSourceController {


    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SysBaseService sysBaseService;


    @ApiOperation(value = "获取所有分类")
    @GetMapping("/get-category")
    public R<List<CategoryEntity>> getCategory() {
      return R.ok(categoryService.list());
    }

    @ApiOperation(value = "获取子分类")
    @GetMapping("/get-category-pi")
    public R<List<CategoryEntity>> getCategoryPi(@RequestParam(required = false) String key,@RequestParam(required = false) String name) {
        if(StringUtils.isEmpty(key)){
            return R.ok(categoryService.list(new LambdaQueryWrapper<CategoryEntity>()
                    .like(!StringUtils.isEmpty(name),CategoryEntity::getName,name)
                    .eq(CategoryEntity::getParentId,"")
                    .or(q->q.isNull(CategoryEntity::getParentId))
                    .orderByAsc(CategoryEntity::getSort)));
        }
        return R.ok(categoryService.list(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getParentId,key)
                .like(!StringUtils.isEmpty(name),CategoryEntity::getName,name)
                .orderByAsc(CategoryEntity::getSort)));

    }

    @ApiOperation(value = "获取商品")
    @PostMapping("/get-product")
    public R<Page<ProductEntity>> getProduct(@RequestBody ProductVO vo) {
        return R.ok(productService.getProduct(vo));
    }

    @ApiOperation(value = "获取两个分类")
    @GetMapping("/get-two-category")
    public R<Object> getTwoProduct() {
        return R.ok(categoryService.getTwoProduct());
    }

    @ApiOperation(value = "获取头部数据")
    @GetMapping("/head")
    public R<Object> head() {
        return R.ok(sysBaseService.head());
    }

    @ApiOperation(value = "获取分类")
    @GetMapping("/class")
    public R<Object> classTo() {
        return R.ok(sysBaseService.classTo());
    }

    @ApiOperation(value = "获取商品详情")
    @GetMapping("/detail")
    public R<ProductEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(productService.selectByPrimaryKey(key));
    }

}
