package com.z.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密(单向加密)  类似的算法还有SHA-256
 * 只可加密无法解密，通常用于签名等操作
 */
public class MD5Utils {

    /**
     * 默认使用16进制
     *
     * @param input
     * @return
     */
    public static String strToMd5(String input) {
        return strToMd5(input, 16);
    }

    /**
     * @param input
     * @param rad   进制，16或32
     * @return
     */
    public static String strToMd5(String input, int rad) {
        //核心对象
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //将输入字符串更改为字节数组
        byte[] inputByte = input.getBytes(StandardCharsets.UTF_8);
        byte[] digest = messageDigest.digest(inputByte);
        BigInteger bigInteger = new BigInteger(1, digest);
        return bigInteger.toString(rad);
    }

}
