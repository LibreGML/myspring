package com.jbj.jbjapi.service;

import com.jbj.jbjapi.entity.CategoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryEntity> getAll(String name);

    Object getTwoProduct();
}
