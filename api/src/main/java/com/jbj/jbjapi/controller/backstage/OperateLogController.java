package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.OperateLogVo;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.SysOperateLogEntity;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.SysOperateLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/operate-log")
public class OperateLogController {
    @Resource
    private SysOperateLogService operateLogService;

    @Resource
    private AccountService accountService;

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<SysOperateLogEntity>> list(@RequestBody OperateLogVo operateLogVo, @RequestParam(required = false) String key) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (account.getRoleType().equals("1") && StringUtils.hasLength(key)) {
            account = accountService.getById(key);
        }
        return R.ok(operateLogService.page(operateLogVo.build(), new LambdaQueryWrapper<SysOperateLogEntity>()
                .eq(SysOperateLogEntity::getRoleId, account.getRoleId())
                .eq(SysOperateLogEntity::getRoleType, account.getRoleType())
                .like(StringUtils.hasLength(operateLogVo.getType()), SysOperateLogEntity::getType, operateLogVo.getType())
                .orderByDesc(SysOperateLogEntity::getCreateTime)
        ));
    }
}
