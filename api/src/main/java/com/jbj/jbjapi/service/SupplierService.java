package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.SupplierVo;
import com.jbj.jbjapi.entity.SupplierEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface SupplierService extends IService<SupplierEntity> {

    Page<SupplierEntity> supplierList(SupplierVo vo);
}
