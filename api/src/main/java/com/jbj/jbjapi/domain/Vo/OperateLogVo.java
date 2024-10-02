package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperateLogVo extends PageQuery {
    @ApiModelProperty("类型")
    private String type;
}
