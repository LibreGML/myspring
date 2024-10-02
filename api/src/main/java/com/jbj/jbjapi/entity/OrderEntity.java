package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName("t_order")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 订单号
     */
    @TableField("order_number")
    private String orderNumber;

    /**
     * 商品id
     */
    @TableField("product_id")
    private String productId;
    /**
     * 商品名
     */
    @TableField("product_name")
    private String productName;

    /**
     * 下单信息
     */
    @TableField("order_info")
    private String orderInfo;

    /**
     * 份数
     */
    @TableField("quantity")
    private Integer quantity;

    @TableField("attach")
    private String attach;

    @TableField("km_data")
    private String kmData;
    /**
     * 支付金额
     */
    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 付款方式
     */
    @TableField("payment_method")
    private String paymentMethod;

    /**
     * 商家ID
     */
    @TableField("merchant_id")
    private String merchantId;

    /**
     * 供应商ID
     */
    @TableField("supplier_id")
    private String supplierId;

    /**
     * 对接返回信息
     */
    @TableField("integration_response")
    private String integrationResponse;

    /**
     * 订单状态 -1下单失败 1下单成功 2申请售后
     */
    @TableField("status")
    private String status;

    /**
     * 商家账户余额
     */
    @TableField("merchant_balance")
    private BigDecimal merchantBalance;

    /**
     * 商家额度包余额
     */
    @TableField("merchant_quota_balance")
    private BigDecimal merchantQuotaBalance;

    /**
     * 订单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("order_time")
    private LocalDateTime orderTime;


}
