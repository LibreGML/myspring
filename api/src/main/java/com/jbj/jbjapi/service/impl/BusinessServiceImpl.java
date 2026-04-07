package com.jbj.jbjapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.BusinessVo;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.mapper.BusinessMapper;
import com.jbj.jbjapi.service.BusinessService;
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
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, BusinessEntity> implements BusinessService {

    @Override
    public Page<BusinessEntity> businessList(BusinessVo vo) {
        return this.baseMapper.businessList(vo.build(), vo);
    }
}
