package com.jbj.jbjapi.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.entity.CategoryEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.entity.SysSourceEntity;
import com.jbj.jbjapi.mapper.SysBaseMapper;
import com.jbj.jbjapi.mapper.SysSourceMapper;
import com.jbj.jbjapi.service.CategoryService;
import com.jbj.jbjapi.service.SysBaseService;
import com.jbj.jbjapi.service.SysSourceService;
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
public class SysBaseServiceImpl extends ServiceImpl<SysBaseMapper, SysBaseEntity> implements SysBaseService {

    @Resource
    private CategoryService categoryService;

    @Override
    public Object head() {
        SysBaseEntity carousel = this.getById("carousel");
        SysBaseEntity hardl = this.getById("twe");
        Map<String, Object> map = new HashMap<>();
        map.put("carousel", carousel.getFieldValue());
        map.put("twe", hardl.getFieldValue());
        return map;
    }

    @Override
    public Object classTo() {
        SysBaseEntity class1 = this.getById("class1");
        SysBaseEntity class2 = this.getById("class2");
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(class1.getFieldValue())) {
            JSONObject jsonObject = JSONUtil.parseObj(class1.getFieldValue());
            JSONArray jsonArray = jsonObject.getJSONArray("category");
            List<String> list1 = new ArrayList<>();
            jsonArray.forEach(res -> {
                JSONArray o = (JSONArray) res;
                list1.add(o.getStr(o.size()-1));
            });
            List<CategoryEntity> list = categoryService.list(new LambdaQueryWrapper<CategoryEntity>()
                    .in(CategoryEntity::getId, list1));
            Map<String,Object> map1  = new HashMap<>();
            map1.put("name",jsonObject.get("name"));
            map1.put("category",list);
            map.put("class1", map1);
        }
        if (!StringUtils.isEmpty(class2.getFieldValue())) {
            JSONObject jsonObject1 = JSONUtil.parseObj(class2.getFieldValue());
            JSONArray jsonArray = jsonObject1.getJSONArray("category");
            List<String> list1 = new ArrayList<>();
            jsonArray.forEach(res -> {
                JSONArray o = (JSONArray) res;
                list1.add(o.getStr(o.size()-1));
            });
            List<CategoryEntity> list = categoryService.list(new LambdaQueryWrapper<CategoryEntity>()
                    .in(CategoryEntity::getId, list1));
            Map<String,Object> map1  = new HashMap<>();
            map1.put("name",jsonObject1.get("name"));
            map1.put("category",list);
            map.put("class2", map1);
        }
        return map;
    }
}
