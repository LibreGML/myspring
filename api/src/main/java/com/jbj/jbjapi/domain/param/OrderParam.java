package com.jbj.jbjapi.domain.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderParam {
    @ApiModelProperty("订单Id")
    private String id;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("商品id")
    private String productId;

    @ApiModelProperty("商品名")
    private String productName;

    @ApiModelProperty("下单信息")
    private String orderInfo;

    @ApiModelProperty("份数")
    private Integer quantity;

    @ApiModelProperty("支付金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty("付款方式")
    private String paymentMethod;

    @ApiModelProperty("商家ID")
    private String merchantId;

    @ApiModelProperty("对接返回信息")
    private String integrationResponse;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("商家账户余额")
    private BigDecimal merchantBalance;

    @ApiModelProperty("商家额度包余额")
    private BigDecimal merchantQuotaBalance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("订单时间")
    private LocalDateTime orderTime;
}
