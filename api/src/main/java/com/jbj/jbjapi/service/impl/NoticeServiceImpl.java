package com.jbj.jbjapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.Vo.NoticeVo;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.NoticeEntity;
import com.jbj.jbjapi.entity.NoticeReadEntity;
import com.jbj.jbjapi.mapper.NoticeMapper;
import com.jbj.jbjapi.service.NoticeReadService;
import com.jbj.jbjapi.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {

    @Resource
    private NoticeReadService noticeReadService;

    @Override
    public Page<NoticeEntity> noticeList(NoticeVo vo) {
        return this.baseMapper.noticeList(vo.build(), vo);
    }

    @Override
    public Page<NoticeEntity> noticeMyList(NoticeVo vo) {
        AccountEntity account = LoginHelper.getLoginUser();
        Page<NoticeEntity> page = this.page(vo.build(), new LambdaQueryWrapper<NoticeEntity>()
                .eq(NoticeEntity::getType, 1).or(q -> q.eq(NoticeEntity::getType, account.getRoleType()))
                .orderByDesc(NoticeEntity::getCreateTime));
        page.getRecords().forEach(res -> {
            boolean isRead = noticeReadService.count(new LambdaQueryWrapper<NoticeReadEntity>()
                    .eq(NoticeReadEntity::getAccountId, account.getId())
                    .eq(NoticeReadEntity::getNoticeId, res.getId())
            ) > 0;
            res.setIsRead(isRead ? "1" : "0");
        });
        return page;
    }
}
