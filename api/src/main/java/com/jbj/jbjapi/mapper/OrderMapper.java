package com.jbj.jbjapi.mapper;

import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface OrderMapper extends BaseMapper<OrderEntity> {

    Double getAllTransaction(@Param("account") AccountEntity account);

    List<Map<String, String>> getTransactionData();

    List<Map<String, String>> category7(@Param("roleType") String roleType, @Param("roleId") String roleId);

    List<Map<String, String>> getBusTransactionData();
}
