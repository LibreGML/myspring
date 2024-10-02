package com.jbj.jbjapi.domain.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Data
public class ProductDelParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "图片路径")
    private String image;

    @ApiModelProperty(value = "详情")
    private String details;

    @ApiModelProperty(value = "下单信息（输入框 1，输入框，2下拉框 data：下拉选择数据）")
    private Object attach;

    @ApiModelProperty(value = "库存")
    private String quota;
}
