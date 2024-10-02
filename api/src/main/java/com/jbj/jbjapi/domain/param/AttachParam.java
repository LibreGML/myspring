package com.jbj.jbjapi.domain.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttachParam {
    @ApiModelProperty("购买附加类型")
    private String attachtype;
    @ApiModelProperty("购买附加值")
    private String value;
}
