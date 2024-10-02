package com.jbj.jbjapi.utils;

import cn.hutool.core.io.file.FileNameUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Url2MultipartFile {
    public static void main(String[] args) throws IOException {
        String url = "http://bpic.588ku.com/element_origin_min_pic/19/03/15/75076c485081d15ed9c224ad3e4ce4a1.jpg";
        MultipartFile fileItem = createFileItem(url);
        File folder = new File("D:/data/pic");
        folder.mkdirs();
        fileItem.transferTo(new File(folder + "/pic.jpg"));
    }

    public static MultipartFile createFileItem(String url) {
        FileItem item = null;
        try {
            String ext = FileNameUtil.extName(url);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                // 创建工厂对象
                FileItemFactory factory = new DiskFileItemFactory(16, null);
                // 通过工厂对象创建 item 对象
                item = factory.createItem("tmp."+ext, ContentType.IMAGE_JPEG.toString(), false, "tmp."+ext);
                item.setFieldName("tmp."+ext);
                OutputStream os = item.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }

        return new CommonsMultipartFile(item);
    }
}