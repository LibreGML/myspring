package com.jbj.jbjapi.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.AccountVo;
import com.jbj.jbjapi.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Mapper
public interface AccountMapper extends BaseMapper<AccountEntity> {

    Page<AccountEntity> accountList(@Param("page") Page<Object> build,@Param("vo") AccountVo accountVo);
}
