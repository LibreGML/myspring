package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@TableName("t_notice")
public class NoticeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公告标题
     */
    @TableField("title")
    private String title;

    /**
     * 公告内容
     */
    @TableField("content")
    private String content;

    /**
     * 公告类型
     */
    @TableField("type")
    private String type;

    /**
     * 微信群码
     */
    @TableField("wechat_group_code")
    private String wechatGroupCode;

    /**
     * 平台站长好友码
     */
    @TableField("platform_friend_code")
    private String platformFriendCode;

    /**
     * 售后码
     */
    @TableField("after_sales_code")
    private String afterSalesCode;

    /**
     * 自定义码
     */
    @TableField("custom_code")
    private String customCode;

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
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField("is_del")
    @TableLogic
    private Boolean isDel;

    @TableField(exist = false)
    private String isRead;
}
