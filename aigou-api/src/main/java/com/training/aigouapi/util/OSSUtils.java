package com.training.aigouapi.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * OSS文件上传工具类
 *
 * @author 十五
 */
public class OSSUtils {

    private static final String ENDPOINT = "https://oss-cn-wuhan-lr.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI5tByXXxngFJF51r4YH4Q";
    private static final String ACCESS_KEY_SECRET = "dLSXxkbyStxoCVQP9oU5L2R7Zw2lFO";
    private static final String BUCKET_NAME = "shiwu-aigou";
    //目录名
    public static String dir = "images";

    /**
     * 文件上传
     *
     * @param inputStream 输入流，用于读取文件数据
     * @param fileName    文件名，用于保存文件
     */
    public static void upload(InputStream inputStream, String fileName) {
        // 创建OSS客户端实例
        OSS oss = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 将文件数据上传到OSS中指定的桶和目录
        oss.putObject(BUCKET_NAME, dir + "/" + fileName, inputStream);
        // 关闭OSS客户端
        oss.shutdown();
    }

    /**
     * 获得文件URL
     * 通过文件名生成一个预签名的URL，用于访问存储在阿里云OSS中的文件
     * 预签名的URL可以在一段时间内访问私有文件，而不需要提供阿里云的认证信息
     *
     * @param fileName 文件名，用于定位OSS中的具体文件
     * @return 返回预签名的URL字符串，用于访问OSS中的文件
     */
    public static String getUrl(String fileName) {
        // 构建OSS客户端
        OSS oss = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        // 设置过期时间
        // 生成预签名URL时，需要指定URL的有效期，这里设置为从当前时间起100天后过期
        LocalDateTime time = LocalDateTime.now().plusDays(100);
        Date expiration = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

        // 生成预签名URL
        // 根据指定的桶名、对象键（包括目录和文件名）和过期时间生成一个预签名的URL
        URL url = oss.generatePresignedUrl(BUCKET_NAME, dir + "/" + fileName, expiration);

        // 关闭OSS客户端
        // 释放OSS客户端占用的资源，避免资源泄露
        oss.shutdown();

        // 返回预签名的URL字符串
        // 将生成的URL转换为字符串并返回，以便在外部使用
        return url.toString();
    }
}
