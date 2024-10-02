package com.jbj.jbjapi.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.entity.CategoryEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.mapper.CategoryMapper;
import com.jbj.jbjapi.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.service.SysBaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Resource
    private SysBaseService sysBaseService;

    @Override
    public List<CategoryEntity> getAll(String name) {
        List<CategoryEntity> list = new ArrayList<>();
        if (StringUtils.isEmpty(name)) {
            list = this.list(new LambdaQueryWrapper<CategoryEntity>()
                    .isNull(CategoryEntity::getParentId).or(e -> e.eq(CategoryEntity::getParentId, ""))
                    .orderByDesc(CategoryEntity::getCreateTime));
            list.forEach(res -> {
                res.setChildren(getChildren(res.getId()));
            });
        } else {
            list = this.list(new LambdaQueryWrapper<CategoryEntity>()
                    .like(CategoryEntity::getName, name)
                    .orderByDesc(CategoryEntity::getCreateTime));
            List<CategoryEntity> listTmp = new ArrayList<>();
            list.forEach(res -> {
                addParent(listTmp, res);
            });
            list.addAll(listTmp);
// 创建一个新的List来存储最终的结果
            List<CategoryEntity> result = new ArrayList<>();
// 遍历原始列表，将parentId为空的CategoryEntity添加到结果列表中
            for (CategoryEntity categoryEntity : list) {
                if (StringUtils.isEmpty(categoryEntity.getParentId())) {
                    result.add(categoryEntity);
                } else {
                    // 在结果列表中查找与parentId相匹配的父对象
                    CategoryEntity parent = result.stream()
                            .filter(child -> child.getId().equals(categoryEntity.getParentId()))
                            .findFirst()
                            .orElse(null);
                    if (parent != null) {
                        // 如果找到父对象，则将当前对象添加到父对象的children列表中
                        parent.getChildren().add(categoryEntity);
                    }
                }
            }
            list = result;
        }
        return list;
    }

    @Override
    public Object getTwoProduct() {
        SysBaseEntity sysBase1 = sysBaseService.getById("source-category1");
        SysBaseEntity sysBase2 = sysBaseService.getById("source-category2");
        Map<String, Object> map = new HashMap<>();
        map.put("first", StringUtils.isEmpty(sysBase1.getFieldValue()) ? null : JSONUtil.parseObj(sysBase1.getFieldValue()));
        map.put("second", StringUtils.isEmpty(sysBase2.getFieldValue()) ? null : JSONUtil.parseObj(sysBase2.getFieldValue()));
        return map;
    }

    private void addParent(List<CategoryEntity> listTmp, CategoryEntity res) {
        if (!StringUtils.isEmpty(res.getParentId())) {
            CategoryEntity category = this.getById(res.getParentId());
            if (category != null) {
                listTmp.add(category);
                addParent(listTmp, category);
            }
        }
    }

    private List<CategoryEntity> getChildren(String id) {
        List<CategoryEntity> list = this.list(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getParentId, id)
                .orderByDesc(CategoryEntity::getCreateTime)
        );
        list.forEach(res -> {
            res.setChildren(getChildren(res.getId()));
        });
        return list;
    }

}
