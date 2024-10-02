package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单与角色关联表
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_source")
public class SysSourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableField("id")
    private String id;

    /**
     * 系统名称
     */
    @TableField("name")
    private String name;


}
