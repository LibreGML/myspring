package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.Vo.SalesVO;
import com.jbj.jbjapi.domain.param.OrderParam;
import com.jbj.jbjapi.domain.param.PurchaseParam;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.MemberOrderEntity;
import com.jbj.jbjapi.entity.OrderEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface MemberOrderService extends IService<MemberOrderEntity> {

}
