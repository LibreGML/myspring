package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private String menuId;

    /**
     * 菜单父ID, 一级菜单父ID为1
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField("menu_title")
    private String menuTitle;

    /**
     * 菜单图标
     */
    @TableField("menu_icon")
    private String menuIcon;


    /**
     * 编码
     */
    @TableField("menu_code")
    private String menuCode;


    /**
     * 菜单（路由）地址
     */
    @TableField("route_path")
    private String routePath;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    @TableField(exist = false)
    private List<SysMenuEntity> listMenus;
}
