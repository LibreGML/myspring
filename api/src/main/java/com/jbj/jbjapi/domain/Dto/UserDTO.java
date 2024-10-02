package com.jbj.jbjapi.domain.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mhw
 * @date 2022/10/25 13:32
 */
@Data
public class UserDTO {

    @ApiModelProperty("账号名")
    private String userAccount;

    @ApiModelProperty("密码")
    private String userPassword;

    @ApiModelProperty("角色")
    private String roleId;

    @ApiModelProperty("头像")
    private String headPic;

    @ApiModelProperty("角色类型")
    private String roleType;
}
