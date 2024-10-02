package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryVO extends PageQuery {
    @ApiModelProperty("父类")
    private String parentId;
}
