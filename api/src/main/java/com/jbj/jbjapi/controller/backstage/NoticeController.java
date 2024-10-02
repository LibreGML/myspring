package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.NoticeVo;
import com.jbj.jbjapi.domain.Vo.SupplierVo;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.CategoryService;
import com.jbj.jbjapi.service.NoticeReadService;
import com.jbj.jbjapi.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "公告", tags = "公告")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    @Resource
    private NoticeReadService noticeReadService;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<NoticeEntity> selectByPrimaryKey(@RequestParam String key) {
        return R.ok(noticeService.getById(key));
    }

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<NoticeEntity>> list(@RequestBody NoticeVo vo) {
        return R.ok(noticeService.noticeList(vo));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip="公告数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody NoticeEntity entity) {
        return R.ok(noticeService.save(entity));
    }

    @ApiOperation(value = "更新")
    @OperateLog(tip="公告数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody NoticeEntity entity) {
        return R.ok(noticeService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip="公告数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        return R.ok(noticeService.removeById(key));
    }

    @ApiOperation(value = "个人列表")
    @PostMapping("/list/my")
    public R<Page<NoticeEntity>> myList(@RequestBody NoticeVo vo) {
        return R.ok(noticeService.noticeMyList(vo));
    }

    @ApiOperation(value = "查看公告")
    @GetMapping("/read")
    public R<Boolean> read(String noticeId) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (noticeReadService.count(new LambdaQueryWrapper<NoticeReadEntity>()
                .eq(NoticeReadEntity::getNoticeId, noticeId)
                .eq(NoticeReadEntity::getAccountId, account.getId())
        ) == 0) {
            NoticeReadEntity noticeReadEntity = new NoticeReadEntity();
            noticeReadEntity.setNoticeId(noticeId);
            noticeReadEntity.setAccountId(account.getId());
            noticeReadService.save(noticeReadEntity);
        }
        return R.ok();
    }

    @ApiOperation(value = "获取未读公告")
    @GetMapping("/unread")
    public R<Integer> read() {
        AccountEntity account = LoginHelper.getLoginUser();
        List<NoticeReadEntity> list = noticeReadService.list(new LambdaQueryWrapper<NoticeReadEntity>()
                .select(NoticeReadEntity::getNoticeId)
                .eq(NoticeReadEntity::getAccountId, account.getId())
        );
        List<String> read = list.stream()
                .map(NoticeReadEntity::getNoticeId) // 提取getNoticeId字段
                .collect(Collectors.toList());

        return R.ok(noticeService.count(new LambdaQueryWrapper<NoticeEntity>()
                .and(qq->qq.eq(NoticeEntity::getType, 1).or(q -> q.eq(NoticeEntity::getType, account.getRoleType())))
                .notIn(read.size()>0,NoticeEntity::getId, read))
        );
    }
}
