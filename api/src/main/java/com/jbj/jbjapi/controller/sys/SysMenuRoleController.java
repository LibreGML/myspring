package com.jbj.jbjapi.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.entity.SysMenuEntity;
import com.jbj.jbjapi.entity.SysMenuRoleEntity;
import com.jbj.jbjapi.service.SysMenuRoleService;
import com.jbj.jbjapi.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单与角色关联表 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "菜单与角色关联表", tags = "菜单与角色关联表")
@RestController
@RequestMapping("/sys-menu-role-entity")
public class SysMenuRoleController {
    @Resource
    private SysMenuRoleService sysMenuRoleService;


    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<SysMenuRoleEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(sysMenuRoleService.getById(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<SysMenuRoleEntity>> list() {
        return R.ok(null);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysMenuRoleEntity entity) {
        return R.ok(sysMenuRoleService.save(entity));
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody SysMenuRoleEntity entity) {
        return R.ok(sysMenuRoleService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(sysMenuRoleService.removeById(key));
    }
}
