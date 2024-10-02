package com.jbj.jbjapi.domain.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePasswordVO {

    @ApiModelProperty("老密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

}
