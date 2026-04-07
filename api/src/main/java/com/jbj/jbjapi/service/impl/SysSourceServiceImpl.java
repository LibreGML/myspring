package com.jbj.jbjapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.entity.SysMenuRoleEntity;
import com.jbj.jbjapi.entity.SysSourceEntity;
import com.jbj.jbjapi.mapper.SysMenuRoleMapper;
import com.jbj.jbjapi.mapper.SysSourceMapper;
import com.jbj.jbjapi.service.SysMenuRoleService;
import com.jbj.jbjapi.service.SysSourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单与角色关联表 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class SysSourceServiceImpl extends ServiceImpl<SysSourceMapper, SysSourceEntity> implements SysSourceService {

}
