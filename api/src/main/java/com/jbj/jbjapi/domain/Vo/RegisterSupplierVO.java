package com.jbj.jbjapi.domain.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterSupplierVO {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("货源")
    private String sourceSystem;

    @ApiModelProperty("网址")
    private String sourceWebsite;

    @ApiModelProperty("对接账号")
    private String dockingAccount;

    @ApiModelProperty("对接密匙")
    private String dockingKey;

    @ApiModelProperty("申请理由")
    private String applyReason;

}
