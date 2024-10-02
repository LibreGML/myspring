package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_source")
public class SourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("supplier_id")
    private String supplierId;

    @TableField(exist = false)
    private String accountId;

    @TableField(exist = false)
    private String supplierName;

    @TableField("examine")
    private String examine;

    @TableField("examine_reason")
    private String examineReason;

    /**
     * 是否删除
     */
    @TableField("is_del")
    @TableLogic
    private Boolean isDel;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private String updateTime;

    /**
     * 1 storageService 2 caihongService 3 kakaService
     */
    @TableField("source_system")
    private String sourceSystem;

    @TableField("source_website")
    private String sourceWebsite;

    @TableField("docking_account")
    private String dockingAccount;

    @TableField("docking_key")
    private String dockingKey;

    @TableField("connectivity")
    private String connectivity;

    @TableField("apply_reason")
    private String applyReason;

    @TableField(exist = false)
    private String url;

    @TableField(exist = false)
    private String balance;
}
