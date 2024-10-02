package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.entity.SysSourceEntity;

/**
 * <p>
 * 菜单与角色关联表 服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface SysBaseService extends IService<SysBaseEntity> {

    Object head();

    Object classTo();
}
