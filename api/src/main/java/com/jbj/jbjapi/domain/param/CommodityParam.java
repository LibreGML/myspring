package com.jbj.jbjapi.domain.param;

import lombok.Data;

import java.util.List;

@Data
public class CommodityParam {
    private String id;
    private String cid;
    private String name;
    private String price;
    private String imgUrl;

    // 0 未提交 1未审核 2通过 3失败
    private String state;

    // 0原因
    private String reason;

    private String productId;

    private List<CommodityParam> children;
    //  1分类 2商品
    private String type;

    private String sourceId;
}
