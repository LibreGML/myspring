package com.jbj.jbjapi.utils;

import java.io.UnsupportedEncodingException;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {

    public static String getSign(Map<String, String> params, String key) {
        String str = filterAndSortParams(params, key);
        return sign(str);
    }


    // 过滤字节类型参数和sign、sign_type参数
    public static String filterAndSortParams(Map<String, String> params, String apiKey) {
        Map<String, String> filteredParams = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!(key.equals("sign") || key.equals("sign_type"))) {
                filteredParams.put(key, value);
            }
        }

        Map<String, String> sortedParams = new TreeMap<>();
        for (Map.Entry<String, String> entry : filteredParams.entrySet()) {
            sortedParams.put(entry.getKey(), entry.getValue());
        }

        return buildSignRequest(sortedParams, apiKey);
    }

    // 拼接待签名字符串和生成签名（示例中使用模拟的MD5签名方法）
    private static String buildSignRequest(Map<String, String> sortedParams, String apiKey) {
        StringBuilder signRequest = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            signRequest.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        signRequest.deleteCharAt(signRequest.length() - 1).append(apiKey);
        return signRequest.toString(); // 返回待签名字符串和签名结果（MD5）
    }


    /**
     * 签名字符串
     *
     * @return 签名结果
     */

    public static String sign(String prestr) {
        return getMD5(prestr);
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes("utf-8")); // 明确指定字符集
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}