package com.jbj.jbjapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.MemberOrderEntity;
import com.jbj.jbjapi.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Mapper
public interface MemberOrderMapper extends BaseMapper<MemberOrderEntity> {

}
