package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@TableName("t_supplier")
public class SupplierEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(exist = false)
    private String accountId;

    @ApiModelProperty("账号")
    @TableField(exist = false)
    private String account;

    @ApiModelProperty("头像")
    @TableField(exist = false)
    private String headPic;

    /**
     * 商家名称
     */
    @TableField("name")
    private String name;

    /**
     * 总订单量
     */
    @TableField("total_orders")
    private Integer totalOrders;

    @TableField("examine")
    private Integer examine;

    @TableField("examine_reason")
    private String examineReason;

    /**
     * 余额剩余
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
    private String updateTime;

}
