package com.jbj.jbjapi.domain.param;

import lombok.Data;

@Data
public class YhOrderParam {
    private String type;
    private String out_trade_no;
    private String notify_url;
    private String return_url;
    private String name;
    private String money;
    private String sitename;
}
