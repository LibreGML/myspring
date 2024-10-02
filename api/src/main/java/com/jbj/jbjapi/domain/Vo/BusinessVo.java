package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessVo extends PageQuery {
    @ApiModelProperty("账号id")
    private String accountId;

    @ApiModelProperty("状态")
    private String status;
}