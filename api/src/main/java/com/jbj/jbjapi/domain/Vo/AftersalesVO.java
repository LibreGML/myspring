package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AftersalesVO  extends PageQuery {
    @ApiModelProperty("订单")
    private String order;
}