package com.jbj.jbjapi.domain.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesVO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("理由")
    private String reason;
}
