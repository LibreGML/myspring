package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("t_withdrawal")
public class WithdrawalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 提现用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 提现金额
     */
    @TableField("withdrawal_amount")
    private BigDecimal withdrawalAmount;

    /**
     * 到账金额
     */
    @TableField("account_amount")
    private BigDecimal accountAmount;

    @TableField("free")
    private BigDecimal free;

    /**
     * 提现二维码
     */
    @TableField("image_code")
    private String imageCode;

    /**
     * 提现状态 1待处理，2驳回，3已处理
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

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
