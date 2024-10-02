package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductVO extends PageQuery {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("审核状态")
    private String examine;
}
