package com.training.aigouapi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ethan
 */
public class MD5Utils {

    // MD5加密
    public static String md5(String input) {
        try {
            // 创建MessageDigest对象，指定MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 执行加密操作
            byte[] bytes = md.digest(input.getBytes());
            // 将byte数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不存在", e);
        }
    }
//    public static void main(String[] args) {
//        String input = "123";
//        String encrypted = MD5Utils.md5(input);
//        System.out.println("MD5加密结果: " + encrypted);
//        //结果：202cb962ac59075b964b07152d234b70
//    }
}
