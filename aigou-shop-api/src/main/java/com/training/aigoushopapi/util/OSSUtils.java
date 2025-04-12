package com.training.aigoushopapi.util;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.comm.SignVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * OSS文件上传工具类
 *
 * @author 十五
 */
public class OSSUtils {

    private static final String ENDPOINT = "https://oss-cn-wuhan-lr.aliyuncs.com";
    private static final String BUCKET_NAME = "shiwu-aigou";
    private static final String REGION = "cn-wuhan-lr";
    //目录名
    public static String dir = "images";

    /**
     * 文件上传
     *
     * @param inputStream 输入流，用于读取文件数据
     * @param fileName    文件名，用于保存文件
     */
    public static void upload(InputStream inputStream, String fileName) {
        // 创建ClientBuilderConfiguration实例
        ClientBuilderConfiguration config = new ClientBuilderConfiguration();
        // 设置使用V4签名算法
        config.setSignatureVersion(SignVersion.V4);
        // 设置连接超时时间
        config.setConnectionTimeout(5000);
        // 设置Socket超时时间
        config.setSocketTimeout(5000);
        // 设置最大重试次数
        config.setMaxErrorRetry(3);
        // 设置协议
        config.setProtocol(Protocol.HTTPS);

        try {
            // 检查环境变量
            String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
            String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");

            if (accessKeyId == null || accessKeySecret == null) {
                throw new RuntimeException("请设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET");
            }

            // 从环境变量获取凭证
            EnvironmentVariableCredentialsProvider credentialsProvider =
                    CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            // 创建OSS客户端实例
            OSS oss = OSSClientBuilder.create()
                    .endpoint(ENDPOINT)
                    .credentialsProvider(credentialsProvider)
                    .region(REGION)
                    .clientConfiguration(config)
                    .build();

            try {
                // 检查Bucket是否存在
                if (!oss.doesBucketExist(BUCKET_NAME)) {
                    throw new RuntimeException("Bucket不存在: " + BUCKET_NAME);
                }

                // 将文件数据上传到OSS中指定的桶和目录
                oss.putObject(BUCKET_NAME, dir + "/" + fileName, inputStream);
            } finally {
                // 关闭OSS客户端
                oss.shutdown();
            }
        } catch (OSSException oe) {
            throw new RuntimeException("OSS服务异常: " + oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            throw new RuntimeException("客户端异常: " + ce.getMessage(), ce);
        } catch (Exception e) {
            throw new RuntimeException("上传文件到OSS失败", e);
        }
    }

    /**
     * 获得文件URL
     * 由于Bucket是公共读的，直接返回公共访问URL
     *
     * @param fileName 文件名，用于定位OSS中的具体文件
     * @return 返回公共访问URL
     */
    public static String getUrl(String fileName) {
        // 直接拼接公共访问URL
        return "https://" + BUCKET_NAME + "." + ENDPOINT.replace("https://", "") + "/" + dir + "/" + fileName;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        try {
            // 测试文件路径，请替换为实际的文件路径
            String testFilePath = "C:/Users/十五/Desktop/1907683991101468674.jpg";
            File testFile = new File(testFilePath);

            if (!testFile.exists()) {
                System.out.println("测试文件不存在: " + testFilePath);
                return;
            }

            // 生成唯一的文件名
            String fileName = System.currentTimeMillis() + "_test.jpg";

            System.out.println("开始上传文件...");
            System.out.println("原始文件路径: " + testFilePath);
            System.out.println("目标文件名: " + fileName);

            // 上传文件
            try (InputStream inputStream = new FileInputStream(testFile)) {
                upload(inputStream, fileName);
                System.out.println("文件上传成功！");

                // 获取文件URL
                String fileUrl = getUrl(fileName);
                System.out.println("文件访问URL: " + fileUrl);
            }

        } catch (Exception e) {
            System.err.println("测试过程中发生错误:");
            e.printStackTrace();
        }
    }
}
