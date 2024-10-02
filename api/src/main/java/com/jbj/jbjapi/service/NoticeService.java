package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.NoticeVo;
import com.jbj.jbjapi.entity.NoticeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface NoticeService extends IService<NoticeEntity> {

    Page<NoticeEntity> noticeList(NoticeVo vo);

    Page<NoticeEntity> noticeMyList(NoticeVo vo);
}
