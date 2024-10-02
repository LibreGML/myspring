package com.jbj.jbjapi.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class SignUtil {

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("timestamp",String.valueOf(System.currentTimeMillis()) );
        String fd = encryption(param, "2ad12466-d3ed-4583-8d97-c138d478c369");
        System.out.println(fd);
    }

    public static String encryption(Map<String, String> param, String key) {
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
        byte[] paddedKey = padKey(key.getBytes(StandardCharsets.UTF_8));
        SymmetricCrypto sm4 = SmUtil.sm4(paddedKey);
        return sm4.encryptHex(signParsString);
    }

    public static Map<String, String> decryption(String signParsString, String key) {
        byte[] paddedKey = padKey(key.getBytes(StandardCharsets.UTF_8));
        SymmetricCrypto sm4 = SmUtil.sm4(paddedKey);
        String decryptedText = sm4.decryptStr(signParsString);
        Map<String, String> param = new HashMap<>();
        String[] keyValuePairs = decryptedText.split("&");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String k = keyValue[0].trim();
                String v = keyValue[1].trim();
                param.put(k, v);
            }
        }
        return param;
    }

    private static byte[] padKey(byte[] key) {
        byte[] paddedKey = new byte[16];
        if (key.length >= 16) {
            System.arraycopy(key, 0, paddedKey, 0, 16);
        } else {
            System.arraycopy(key, 0, paddedKey, 0, key.length);
            for (int i = key.length; i < 16; i++) {
                paddedKey[i] = 0x00;
            }
        }
        return paddedKey;
    }

}