package com.jbj.jbjapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.NoticeVo;
import com.jbj.jbjapi.entity.MemberEntity;
import com.jbj.jbjapi.entity.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {

}
