package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderVO extends PageQuery {
    @ApiModelProperty("订单号")
    private String oder;
}
