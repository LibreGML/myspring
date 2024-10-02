package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.PageQuery;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.AccountVo;
import com.jbj.jbjapi.domain.Vo.LoginVO;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.MemberEntity;
import com.jbj.jbjapi.entity.SupplierEntity;
import com.jbj.jbjapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "定价", tags = "定价")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<MemberEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(memberService.getById(key));
    }


    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<MemberEntity>> list(@RequestBody PageQuery pageQuery) {
        return R.ok(memberService.page(pageQuery.build(), new LambdaQueryWrapper<MemberEntity>().orderByDesc(MemberEntity::getCreateTime)));
    }

    @ApiOperation(value = "会员列表")
    @PostMapping("/member/list")
    public R<Page<MemberEntity>> memberList(@RequestBody PageQuery pageQuery) {
        return R.ok(memberService.page(pageQuery.build(), new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getType, "1").orderByDesc(MemberEntity::getCreateTime)));
    }

    @ApiOperation(value = "额度包列表")
    @PostMapping("/pic/list")
    public R<Page<MemberEntity>> picList(@RequestBody PageQuery pageQuery) {
        return R.ok(memberService.page(pageQuery.build(), new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getType, "2").orderByDesc(MemberEntity::getCreateTime)));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip = "定价数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody MemberEntity memberEntity) {
        return R.ok(memberService.save(memberEntity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip = "定价数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody MemberEntity memberEntity) {
        return R.ok(memberService.updateById(memberEntity));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip = "定价数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(memberService.removeById(key));
    }

}
