package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.SupplierVo;
import com.jbj.jbjapi.entity.SupplierEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.service.SysBaseService;
import com.jbj.jbjapi.source.storage.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "后台数据", tags = "后台数据")
@RestController
@RequestMapping("/website")
public class WebsiteController {

    @Resource
    private SysBaseService sysBaseService;

    @ApiOperation(value = "获取基本数据")
    @GetMapping("/get-base")
    public R<SysBaseEntity> getBase(@RequestParam String key) {
        return R.ok(sysBaseService.getById(key));
    }

    @ApiOperation(value = "保存修改基本数据")
    @OperateLog(tip="后台数据保存修改")
    @PostMapping("/base")
    public R<Boolean> base(@RequestBody SysBaseEntity entity) {
        return R.ok(sysBaseService.saveOrUpdate(entity));
    }

}
