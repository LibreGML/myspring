package com.jbj.jbjapi.controller.sys;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.SysLocalStorage;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.service.SysBaseService;
import com.jbj.jbjapi.service.UploadService;
import com.jbj.jbjapi.source.SignUtil;
import com.jbj.jbjapi.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author mhw
 * @Version 1.0
 */
@Validated
@Api(value = "文件上传（本地服务器）", tags = {"文件上传"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/localFile")
public class LocalUploadController {

    @Resource
    private UploadService uploadService;

    @Resource
    private SysBaseService sysBaseService;


    /**
     * 文件上传（本地服务器）
     */
    @ApiOperation("文件上传（本地服务器）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataTypeClass = File.class, required = true)
    })
    @PostMapping("/upload")
    public R<Map<String, String>> upload(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        SysBaseEntity sysBase = sysBaseService.getById("123pan");
        if (sysBase != null) {
            JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
            if (jsonObject.getBool("open")) {
                return R.ok(uploadService.upload123(file, request, jsonObject));
            }
        }
        return R.ok(uploadService.upload(file, request));
    }
}
