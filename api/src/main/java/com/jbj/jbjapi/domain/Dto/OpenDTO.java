package com.jbj.jbjapi.domain.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mhw
 * @date 2022/10/25 13:32
 */
@Data
public class OpenDTO {

    @ApiModelProperty("账号")
    private String userId;

    @ApiModelProperty("加密数据")
    private String encrypted;
}
