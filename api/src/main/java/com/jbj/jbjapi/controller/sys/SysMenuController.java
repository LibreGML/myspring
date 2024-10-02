package com.jbj.jbjapi.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.entity.AftersalesEntity;
import com.jbj.jbjapi.entity.SysMenuEntity;
import com.jbj.jbjapi.service.AftersalesService;
import com.jbj.jbjapi.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "菜单表", tags = "菜单表")
@RestController
@RequestMapping("/sys-menu-entity")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;


    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<SysMenuEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(sysMenuService.getById(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<SysMenuEntity>> list() {
        return R.ok(null);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysMenuEntity entity) {
        return R.ok(sysMenuService.save(entity));
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody SysMenuEntity entity) {
        return R.ok(sysMenuService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(sysMenuService.removeById(key));
    }
}
