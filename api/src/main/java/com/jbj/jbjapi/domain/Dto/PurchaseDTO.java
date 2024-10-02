package com.jbj.jbjapi.domain.Dto;

import com.jbj.jbjapi.domain.param.AttachParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PurchaseDTO {

    @ApiModelProperty("商品Id")
    private String productId;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty("购买附加信息")
    private List<String> attach;
    private String url;
}
