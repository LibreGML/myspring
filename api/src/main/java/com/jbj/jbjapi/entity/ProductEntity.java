package com.jbj.jbjapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

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
@TableName("t_product")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("supplier_id")
    private String supplierId;

    @TableField("source_id")
    private String sourceId;

    @TableField("product_id")
    private String productId;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    @TableField("price")
    private String price;

    /**
     * 库存
     */
    @TableField(exist = false)
    private String inventory;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private String categoryId;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private Object attach;

    @TableField(exist = false)
    private Object attachPam;

    /**
     * 审核状态 1待审核 2通过 3失败
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;


    @TableField("reason")
    private String reason;
    /**
     * 图片路径
     */
    @TableField("image")
    private String image;

    /**
     * 详情
     */
    @TableField("details")
    private String details;

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


    public BigDecimal getPriceAsBigDecimal() {
        if (StringUtils.hasLength(this.price)) {
            return new BigDecimal(this.price);
        } else {
            return null;
        }
    }
}
