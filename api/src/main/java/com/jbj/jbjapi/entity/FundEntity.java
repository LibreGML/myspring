package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("t_fund")
public class FundEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 角色类型 （1站长，2商家，3供应商）
     */
    @TableField("role_type")
    private String roleType;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 信息
     */
    @TableField("info")
    private String info;


    /**
     * 余额
     */
    @TableField("balance")
    private BigDecimal balance;
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
