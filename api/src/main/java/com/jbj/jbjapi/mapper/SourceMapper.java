package com.jbj.jbjapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jbj.jbjapi.entity.SourceEntity;
import com.jbj.jbjapi.entity.SysSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单与角色关联表 Mapper 接口
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Mapper
public interface SourceMapper extends BaseMapper<SourceEntity> {

}
