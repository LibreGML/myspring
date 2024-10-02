package com.jbj.jbjapi.domain.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseParam {
    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("返回信息")
    private String msg;

    @ApiModelProperty("卡密信息")
    private String kmData;
}
