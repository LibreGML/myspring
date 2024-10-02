package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.PageQuery;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.FundVo;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.FundService;
import com.jbj.jbjapi.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "资金", tags = "资金")
@RestController
@RequestMapping("/fund")
public class FundController {

    @Resource
    private FundService fundService;

    @Resource
    private AccountService accountService;

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<FundEntity>> list(@RequestBody FundVo fundVo, @RequestParam(required = false) String key) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (account.getRoleType().equals("1") && StringUtils.hasLength(key)) {
            account = accountService.getById(key);
        }
        return R.ok(fundService.page(fundVo.build(), new LambdaQueryWrapper<FundEntity>()
                .eq(FundEntity::getRoleId, account.getRoleId())
                .eq(FundEntity::getRoleType, account.getRoleType())
                .like(StringUtils.hasLength(fundVo.getType()), FundEntity::getType, fundVo.getType())
                .orderByDesc(FundEntity::getCreateTime)));
    }

}
