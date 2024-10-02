package com.jbj.jbjapi.service;

import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.Vo.SalesVO;
import com.jbj.jbjapi.domain.param.OrderParam;
import com.jbj.jbjapi.domain.param.PurchaseParam;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.OrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jbj.jbjapi.entity.ProductEntity;

import javax.servlet.http.HttpServletRequest;
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
public interface OrderService extends IService<OrderEntity> {

    PurchaseParam purchase(PurchaseDTO purchase, BusinessEntity business);

    List<OrderParam> order(String order, BusinessEntity business);

    Boolean sales(SalesVO salesVO);

    Double getAllTransaction(AccountEntity account);

    List<Map<String,String>> getTransactionData();

    List<Map<String,String>> category7(String roleType, String roleId);

    List<Map<String, String>> getBusTransactionData();

    Boolean mentary(String key, HttpServletRequest request);

    void getAllDea(List<ProductEntity> list);
}
