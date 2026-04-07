package com.jbj.jbjapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.SupplierVo;
import com.jbj.jbjapi.entity.SupplierEntity;
import com.jbj.jbjapi.mapper.SupplierMapper;
import com.jbj.jbjapi.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, SupplierEntity> implements SupplierService {

    @Override
    public Page<SupplierEntity> supplierList(SupplierVo vo) {
        return this.baseMapper.supplierList(vo.build(), vo);
    }
}
