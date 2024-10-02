package com.jbj.jbjapi.domain.Vo;

import com.jbj.jbjapi.entity.AccountEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mhw
 * @date 2022/10/25 16:11
 */
@Data
public class LoginVO {

    @ApiModelProperty("返回token")
    private String token;

    @ApiModelProperty("用户信息")
    private AccountEntity user;

}
