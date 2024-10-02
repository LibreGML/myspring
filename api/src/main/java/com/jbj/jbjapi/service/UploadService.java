package com.jbj.jbjapi.service;

import cn.hutool.json.JSONObject;
import com.jbj.jbjapi.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface UploadService {
    Map<String, String> upload(MultipartFile file, HttpServletRequest request) throws IOException;

    Map<String, String> upload123(MultipartFile file, HttpServletRequest request, JSONObject jsonObject) throws IOException;

    void zhuancImg(ProductEntity productEntity, String type, HttpServletRequest request, Boolean ispic);
}
