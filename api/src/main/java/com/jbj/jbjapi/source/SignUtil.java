package com.jbj.jbjapi.source;

import org.apache.commons.codec.digest.DigestUtils;
import cn.hutool.crypto.digest.MD5;

import java.util.*;

public class SignUtil {

    public static String getKkySign(Map<String, String> param, String userkey) {
        List<String> keys = new ArrayList<>(param.keySet());
        Collections.sort(keys);
        String signtext = "";
        for (String key : keys) {
            String val = param.get(key);
            if (val == null || val.isEmpty() || key.equals("sign")) {
                continue;
            }
            if (!signtext.isEmpty()) {
                signtext += "&";
            }
            signtext += key + "=" + val;
        }
        String newSign = MD5.create().digestHex(signtext + userkey);
        return newSign;
    }

    public static Map<String, String> getParam(String sign, String key) {
        Map<String, String> param = new HashMap<>();
        String[] signArr = sign.split("&");
        for (String s : signArr) {
            String[] kv = s.split("=");
            param.put(kv[0], kv[1]);
        }
        return param;
    }

    public static String sign(Map<String, String> param, String key) {
        List<String> keys = new ArrayList<>(param.keySet());
        Collections.sort(keys);

        StringBuilder signPars = new StringBuilder();
        for (String k : keys) {
            String v = param.get(k);
            k = k.trim();
            v = v.trim();
            if (!k.equals("sign") && !v.equals("")) {
                signPars.append(k).append("=").append(v).append("&");
            }
        }

        String signParsString = signPars.toString().replaceAll("&$", "");
        signParsString += key;

        return md5(signParsString);
    }




    private static String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            // Handle the exception according to your requirement
            return null;
        }
    }

    public static String getAppToken(String appId, String appSecret, String requestURI,String appTimestamp) {
        return DigestUtils.sha1Hex(appId + appSecret + requestURI + appTimestamp);
    }
}