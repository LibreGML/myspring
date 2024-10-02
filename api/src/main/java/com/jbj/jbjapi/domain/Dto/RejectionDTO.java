package com.jbj.jbjapi.domain.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mhw
 * @date 2022/12/30 11:23
 */
@Data
public class RejectionDTO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("拒绝理由")
    private String content;
}
