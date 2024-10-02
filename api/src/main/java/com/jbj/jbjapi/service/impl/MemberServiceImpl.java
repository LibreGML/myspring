package com.jbj.jbjapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.domain.Vo.NoticeVo;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.MemberEntity;
import com.jbj.jbjapi.entity.NoticeEntity;
import com.jbj.jbjapi.entity.NoticeReadEntity;
import com.jbj.jbjapi.mapper.MemberMapper;
import com.jbj.jbjapi.mapper.NoticeMapper;
import com.jbj.jbjapi.service.MemberService;
import com.jbj.jbjapi.service.NoticeReadService;
import com.jbj.jbjapi.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {


}
