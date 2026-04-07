package com.jbj.jbjapi.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.entity.CategoryEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.entity.SysOperateLogEntity;
import com.jbj.jbjapi.mapper.SysBaseMapper;
import com.jbj.jbjapi.mapper.SysOperateLogMapper;
import com.jbj.jbjapi.service.CategoryService;
import com.jbj.jbjapi.service.SysBaseService;
import com.jbj.jbjapi.service.SysOperateLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单与角色关联表 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLogEntity> implements SysOperateLogService {

}
