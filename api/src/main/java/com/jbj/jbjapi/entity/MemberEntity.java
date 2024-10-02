package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_member")
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 名称  1: "会员定价", 2: "额度包定价"
     */
    @TableField("type")
    private String type;

    /**
     * 等级
     */
    @TableField("grade")
    private String grade;

    @TableField("limitb")
    private String limitb;

    /**
     * 定价
     */
    @TableField("price")
    private String price;

    /**
     * 信息
     */
    @TableField("info")
    private String info;


    /**
     * 时长 （天）
     */
    @TableField("duration")
    private String duration;
    /**
     * 是否删除
     */
    @TableField("is_del")
    @TableLogic
    private Boolean isDel;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
