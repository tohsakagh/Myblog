package com.gh.myblog_backend.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * myblog_backend
 * 2022/8/17 15:50
 * Origin
 */
@SuppressWarnings("all")
@Slf4j
public class TencentCloudUtil {

    private static COSClient init() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "xxxxxxxx";
        String secretKey = "xxxxxxxx";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("xxxxxxxx");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public static String upload(MultipartFile file, String path){
        if (file.isEmpty()) {
            return null;
        }
        COSClient cosClient = init();
        try {
            String originalFilename = file.getOriginalFilename();
            String bucketName = "xxxxxxxx";
            //按日期对文件分类
            //获取打当前日期
            String datePath = new DateTime().toString("yyyy_MM_dd_HH_mm_ss");
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = "myblog/" + path + "/" + datePath + "_" +  originalFilename;
            File toFile = MultipartFileToFileUtils.multipartFileToFile(file);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, toFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            String url = "https://" + bucketName + ".cos.ap-chongqing.myqcloud.com/" + key;
            log.info("上传" + originalFilename + "到腾讯云对象存储, 地址为" + url);
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }finally {
            cosClient.shutdown();
        }
    }


    public static void delete(String filename, String path){
        if (filename == null){
            return;
        }
        COSClient client = init();
        String bucketName = "xxxxxxxxxx";
        String key = "myblog/" + path + "/info/"  + filename;
        System.out.println(key);
        client.deleteObject(bucketName, key);
        client.shutdown();
    }

}
