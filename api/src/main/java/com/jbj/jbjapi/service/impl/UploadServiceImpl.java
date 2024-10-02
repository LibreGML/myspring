package com.jbj.jbjapi.service.impl;

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
import com.jbj.jbjapi.domain.SysLocalStorage;
import com.jbj.jbjapi.entity.ProductEntity;
import com.jbj.jbjapi.entity.SysBaseEntity;
import com.jbj.jbjapi.service.ProductService;
import com.jbj.jbjapi.service.SysBaseService;
import com.jbj.jbjapi.service.UploadService;
import com.jbj.jbjapi.utils.RedisUtil;
import com.jbj.jbjapi.utils.Url2MultipartFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UploadServiceImpl implements UploadService {
    @Resource
    private RedisUtil redisUtil;

    /**
     * 请求 url 中的资源映射，不推荐写死在代码中，最好提供可配置，如 /uploadFiles/**
     */
    @Value("${uploadFile.resourceHandler}")
    private String resourceHandler;
    /**
     * 上传文件保存的本地目录，使用 @Value获 取全局配置文件中配置的属性值，如 E:/wmx/uploadFiles/
     */
    @Value("${uploadFile.location}")
    private String uploadFileLocation;

    @Value("${uploadFile.domain}")
    private String domain;

    @Resource
    private SysBaseService sysBaseService;

    @Resource
    private ProductService productService;

    @Override
    public Map<String, String> upload(MultipartFile file, HttpServletRequest request) throws IOException {
        Assert.notNull(file, "上传文件不能为空");
        String basePath = domain + request.getContextPath();
        String fileName = file.getOriginalFilename();
        //获取文件后缀，例如txt
        String ext = FileNameUtil.extName(fileName);
        fileName = IdUtil.simpleUUID() + "." + ext;
        String filePath = File.separator + DateUtil.thisYear() + File.separator + DateUtil.thisMonth() + File.separator + DateUtil.thisDayOfMonth();
        String fileServerPath = basePath + resourceHandler.substring(0, resourceHandler.lastIndexOf("/") + 1) + DateUtil.thisYear() + "/" + DateUtil.thisMonth() + "/" + DateUtil.thisDayOfMonth() + "/" + fileName;
        assert fileName != null;
        File saveFile = new File(uploadFileLocation + filePath, fileName);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        file.transferTo(saveFile);
        SysLocalStorage oss = SysLocalStorage.builder().fileName(fileName).url(fileServerPath).build();
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("fileName", oss.getFileName());
        return map;
    }

    @Override
    public Map<String, String> upload123(MultipartFile file, HttpServletRequest request, JSONObject base) throws IOException {
        String fileName = file.getOriginalFilename();
        //获取文件后缀，例如txt
        String ext = FileNameUtil.extName(fileName);
        fileName = IdUtil.simpleUUID() + "." + ext;
        String md5Hash = DigestUtils.md5Hex(file.getInputStream());
        long fileSize = file.getSize();
        String body;
        String mak = getMkdir(base);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("parentFileID", mak);
            map.put("filename", fileName);
            map.put("etag", md5Hash);
            map.put("size", fileSize);
            body = HttpRequest.post(base.getStr("uploadAddress") + "/upload/v1/file/create")
                    .header("Authorization", getAccessToken(base))
                    .header("Platform", "open_platform")
                    .body(JSONUtil.toJsonStr(map))
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getInt("code") != 0) {
                throw new ServiceException("123云盘异常");
            }
            JSONObject data = jsonObject.getJSONObject("data");
            Map<String, String> map1 = new HashMap<>(2);
            map1.put("fileName", fileName);
            String url = base.getStr("accessing") + "/JBJ/" + fileName;
            if (data.getBool("reuse")) {
                map1.put("url", url);
                return map1;
            }

            uploadFileSlices(data, file, base);
            map1.put("url", url);
            return map1;
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }

    @Override
    @Async
    public void zhuancImg(ProductEntity productEntity, String type, HttpServletRequest request,Boolean ispic) {
        if (type.equals("1") || type.equals("2")) {
            if(ispic){
                String url = getChangeUrl(productEntity.getImage(), type, request);
                if (StringUtils.hasLength(url)) {
                    productEntity.setImage(url);
                }
            }
            String details = getDetailsUrl(productEntity.getDetails(), type, request);
            if (StringUtils.hasLength(details)) {
                productEntity.setDetails(details);
            }
            productService.updateById(productEntity);
        }
    }

    private String getDetailsUrl(String details, String type, HttpServletRequest request) {
        // 正则表达式匹配图片或视频的网络链接
        String regex = "(https?://\\S+\\.(?:png|jpe?g|gif|mp4|mov|avi))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(details);
        String result = details;
        while (matcher.find()) {
            String url = matcher.group(1);
            String replacement = getChangeUrl(url, type, request);
            result = result.replace(url, replacement);
        }
        return result;
    }

    private String getChangeUrl(String url, String type, HttpServletRequest request) {
        try {
            if (StringUtils.hasLength(type)) {
                Map<String, String> map = null;
                MultipartFile file = Url2MultipartFile.createFileItem(url);
                // 本地
                if (type.equals("1")) {
                    map = upload(file, request);
                }
                if (type.equals("2")) {
                    SysBaseEntity sysBase = sysBaseService.getById("123pan");
                    if (sysBase != null) {
                        JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
                        map = upload123(file, request, jsonObject);
                    }
                }
                if (map != null) {
                    return map.get("url");
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public Map<String, String> uploadZh(MultipartFile file, HttpServletRequest request) throws IOException {
        SysBaseEntity sysBase = sysBaseService.getById("123pan");
        if (sysBase != null) {
            JSONObject jsonObject = JSONUtil.parseObj(sysBase.getFieldValue());
            if (jsonObject.getBool("open")) {
                return upload123(file, request, jsonObject);
            }
        }
        return upload(file, request);
    }

    private void uploadFileSlices(JSONObject data, MultipartFile file, JSONObject base) {
        // 根据文件大小切分文件，1
        byte[] buffer = new byte[data.getInt("sliceSize")]; // 分片缓冲区大小为sliceSize，根据实际情况修改
        try (InputStream inputStream = file.getInputStream()) {
            int offset = 0;
            int sliceNo = 1;
            while (true) {
                int readBytes = inputStream.read(buffer, 0, buffer.length);
                if (readBytes <= 0) {
                    break; // 文件读取完毕，退出循环
                }
                String uploadUrl = get123Url(data.getStr("preuploadID"), sliceNo, base);
                uploadSlice(uploadUrl, data.getStr("preuploadID"), offset, buffer, readBytes); // 上传分片
                offset += readBytes; // 更新偏移量，准备下一段数据读取和上传
                sliceNo += 1;
            }
            uploadComplete(base, data.getStr("preuploadID"));
        } catch (IOException e) {
            throw new ServiceException("上传异常"); // 如果发生异常，直接抛出异常信息，以便后续处理或记录日志等操作。
        }
    }

    private void uploadComplete(JSONObject base, String preuploadID) {
        Map<String, Object> map = new HashMap<>();
        map.put("preuploadID", preuploadID);
        String body;
        try {
            body = HttpRequest.get(base.getStr("uploadAddress") + "/upload/v1/file/upload_complete")
                    .header("Authorization", getAccessToken(base))
                    .header("Platform", "open_platform")
                    .body(JSONUtil.toJsonStr(map))
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getInt("code") != 0) {
                throw new ServiceException("123云盘异常");
            }
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }

    private void uploadSlice(String uploadUrl, String preuploadID, int offset, byte[] buffer, int readBytes) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(uploadUrl); // 根据实际的上传URL创建URL对象
            connection = (HttpURLConnection) url.openConnection(); // 打开连接对象，准备上传数据。这里需要根据实际情况设置请求方法和请求头等信息。
            connection.setRequestMethod("PUT"); // 设置请求方法为PUT，根据实际情况修改。同时还需要设置请求头中的其他参数，如Content-Length等。这里需要根据实际情况修改。
            connection.setDoOutput(true); // 设置允许输出数据到连接中。这里需要根据实际情况修改。同时还需要设置请求头中的其他参数，如Content-Type等。这里需要根据实际情况修改。

            // 设置请求头中的分片信息
            connection.setRequestProperty("X-Upload-ID", preuploadID);
            connection.setRequestProperty("X-Upload-Offset", String.valueOf(offset));
            connection.setRequestProperty("Content-Length", String.valueOf(readBytes));

            // 获取输入流并写入数据到连接中
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(buffer, 0, readBytes);
                outputStream.flush(); // 刷新输出流，确保数据完全写入连接中
            }

            // 获取响应码和响应内容
            int responseCode = connection.getResponseCode();
            String responseContent = connection.getResponseMessage();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Content: " + responseContent);
        } catch (IOException e) {
            throw e; // 如果发生异常，直接抛出异常信息，以便后续处理或记录日志等操作。
        } finally {
            if (connection != null) {
                connection.disconnect(); // 关闭连接对象
            }
        }
    }


    private void getUser(JSONObject base) {
        String body;
        try {
            body = HttpRequest.get(base.getStr("uploadAddress") + "/api/v1/user/info")
                    .header("Authorization", getAccessToken(base))
                    .header("Platform", "open_platform")
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getInt("code") != 0) {
                throw new ServiceException("123云盘异常");
            }
            JSONObject data = jsonObject.getJSONObject("data");
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }

    private String getMkdir(JSONObject base) {
        Object diri = redisUtil.get("JBJDIRID:" + base.getStr("clientId"));
        if (!Objects.isNull(diri)) {
            return String.valueOf(diri);
        }
        String dirID = getFileList("0", "JBJ", base);
        if (StringUtils.hasLength(dirID)) {
            redisUtil.set("JBJDIRID:" + base.getStr("clientId"), dirID);
            return dirID;
        }
        String body;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "JBJ");
            map.put("parentID", "0");
            body = HttpRequest.post(base.getStr("uploadAddress") + "/upload/v1/file/mkdir")
                    .header("Authorization", getAccessToken(base))
                    .header("Platform", "open_platform")
                    .body(JSONUtil.toJsonStr(map))
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getInt("code") != 0) {
                throw new ServiceException("123云盘异常");
            }
            JSONObject data = jsonObject.getJSONObject("data");
            redisUtil.set("JBJDIRID:" + base.getStr("clientId"), data.getStr("dirID"));
            return data.getStr("dirID");
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }

    private String getFileList(String parentFileId, String searchData, JSONObject base) {
        String dirID = "";
        String body;
        try {
            body = HttpRequest.get(base.getStr("uploadAddress") + "/api/v1/file/list")
                    .header("Authorization", getAccessToken(base))
                    .header("Platform", "open_platform")
                    .form("parentFileId", parentFileId) // 文件夹ID，根目录传 0
                    .form("page", 1) // 页码数，这里假设为1
                    .form("limit", 10)// 每页文件数量，这里假设为10
                    .form("orderBy", "file_name") // 排序字段,例如:file_id、size、file_name
                    .form("orderDirection", "asc") // 排序方向:asc、desc
                    .form("trashed", false)// 是否查看回收站的文件，这里假设为false
                    .form("searchData", searchData) // 搜索关键字，这里假设为"关键字"
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null && data.getInt("total") > 0) {
                JSONArray jsonArray = data.getJSONArray("fileList");
                for (Object item : jsonArray) {
                    JSONObject object = (JSONObject) item;
                    if (object.getStr("filename").equals("JBJ")) {
                        dirID = object.getStr("fileID");
                    }
                }
            }
            return dirID;
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }

    private String get123Url(String preuploadID, Integer sliceSize, JSONObject base) {
        String body;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("preuploadID", preuploadID);
            map.put("sliceNo", sliceSize);
            body = HttpRequest.post(base.getStr("uploadAddress") + "/upload/v1/file/get_upload_url")
                    .header("Authorization", getAccessToken(base))
                    .header("Platform", "open_platform")
                    .body(JSONUtil.toJsonStr(map))
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getInt("code") != 0) {
                throw new ServiceException("123云盘异常");
            }
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getStr("presignedURL");
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }

    private String getAccessToken(JSONObject base) {
        Object accessToken = redisUtil.get("123yunaccessToken:" + base.getStr("clientId"));
        if (!Objects.isNull(accessToken)) {
            return String.valueOf(accessToken);
        }
        String body;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("clientID", base.getStr("clientId"));
            map.put("clientSecret", base.getStr("clientSecret"));
            body = HttpRequest.post(base.getStr("uploadAddress") + "/api/v1/access_token")
                    .header("Platform", "open_platform")
                    .body(JSONUtil.toJsonStr(map))
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getInt("code") != 0) {
                throw new ServiceException("123云盘异常");
            }
            JSONObject data = jsonObject.getJSONObject("data");
            String access = data.getStr("accessToken");
            LocalDateTime time = data.getLocalDateTime("expiredAt", LocalDateTime.MAX);
            Duration duration = Duration.between(LocalDateTime.now(), time);
//            long seconds = duration.getSeconds() - 60;
            long seconds = 60 * 60;
            redisUtil.set("123yunaccessToken:" + base.getStr("clientId"), access, seconds);
            return access;
        } catch (HttpException e) {
            throw new ServiceException("123云盘异常");
        }
    }


}
