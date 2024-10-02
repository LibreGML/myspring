package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.domain.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CommodityVo extends PageQuery {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("商品名")
    private String proName;

    @ApiModelProperty("货源id")
    private String sourceId;

    private List<Map<String,String>> selectSource;
}
