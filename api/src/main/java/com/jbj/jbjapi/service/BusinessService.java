package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.BusinessVo;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface BusinessService extends IService<BusinessEntity> {

    Page<BusinessEntity> businessList(BusinessVo vo);
}
