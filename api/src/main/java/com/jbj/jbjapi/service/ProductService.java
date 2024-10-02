package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Vo.ProductVO;
import com.jbj.jbjapi.domain.Vo.SourceVo;
import com.jbj.jbjapi.entity.CategoryEntity;
import com.jbj.jbjapi.entity.ProductEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface ProductService extends IService<ProductEntity> {

    Page<ProductEntity> getProduct(ProductVO vo);

    void getPicDetails(ProductEntity res);

    Boolean editFl(Map<String, Object> vo);

    ProductEntity selectByPrimaryKey(String key);

    Boolean updateByPrimaryKey(Map<String, Object> vo, HttpServletRequest request);
}
