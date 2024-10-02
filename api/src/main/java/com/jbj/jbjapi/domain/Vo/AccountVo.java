package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountVo extends PageQuery {
    @ApiModelProperty("账号")
    private String account;
}
