package com.jbj.jbjapi.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jbj.jbjapi.domain.param.YhOrderParam;
import com.jbj.jbjapi.entity.MemberEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.utils.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Resource
    private SysBaseService sysBaseService;

    public String sendPostRequest(YhOrderParam param) throws IOException {
        SysBaseEntity sysBase = sysBaseService.getById("yinghua");
        JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
        Map<String, String> formMap = new HashMap<>();
        formMap.put("pid", jsonObject.getStr("pid"));
        formMap.put("type", param.getType());
        formMap.put("out_trade_no", param.getOut_trade_no());
        formMap.put("notify_url", param.getNotify_url());
        formMap.put("return_url", param.getReturn_url());
        formMap.put("name", param.getName());
        formMap.put("money", param.getMoney());
        formMap.put("sitename", param.getSitename());
        String sign = MD5.getSign(formMap, jsonObject.getStr("key"));
        String buildSignRequest = "pid=" + jsonObject.getStr("pid") + "&type=" + param.getType() + "&out_trade_no="
                + param.getOut_trade_no() + "&notify_url=" + param.getNotify_url()
                + "&return_url=" + param.getReturn_url() + "&name=" + param.getName()
                + "&money=" + param.getMoney() + "&sitename=" + param.getSitename();
        String lastStr = "&sign=" + sign + "&sign_type=MD5";
        return jsonObject.getStr("host") + "/submit.php?" + buildSignRequest + lastStr;
    }

    public boolean isVief(String pid, String tradeNo, String outTradeNo, String type, String name, String money, String tradeStatus, String sign) {
        SysBaseEntity sysBase = sysBaseService.getById("yinghua");
        JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
        Map<String, String> formMap = new HashMap<>();
        formMap.put("pid", pid);
        formMap.put("type", type);
        formMap.put("out_trade_no", outTradeNo);
        formMap.put("trade_no", tradeNo);
        formMap.put("trade_status", tradeStatus);
        formMap.put("name", name);
        formMap.put("money", money);
        String sign1 = MD5.getSign(formMap, jsonObject.getStr("key"));
        return sign1.equals(sign);
    }

//    ?sign=40bd71a5db3db6006ad73e511eab6d4c&sign_type=MD5#/index
}

