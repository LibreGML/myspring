package com.jbj.jbjapi.controller.sys;

import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.entity.SysSourceEntity;
import com.jbj.jbjapi.service.SysSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 货源系统 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "货源系统", tags = "货源系统")
@RestController
@RequestMapping("/sys-source")
public class SysSourceController {
    @Resource
    private SysSourceService sysSourceService;

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<List<SysSourceEntity>> list() {
        return R.ok(sysSourceService.list());
    }

}
