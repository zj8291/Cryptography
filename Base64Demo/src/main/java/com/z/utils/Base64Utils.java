package com.z.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64 是一种基于 64 个可打印字符来表示二进制数据的表示方法。
 * 由于 2⁶ = 64 ，所以每 6 个比特为一个单元，对应某个可打印字符。
 * 3 个字节有 24 个比特，对应于 4 个 base64 单元，即 3 个字节可由 4 个可打印字符来表示。
 * Base64 常用于在处理文本数据的场合，表示、传输、存储一些二进制数据，包括 MIME 的电子邮件及 XML 的一些复杂数据。
 */
public class Base64Utils {
    /**
     * 字符串转base64编码
     *
     * @param input
     * @return
     */
    public static String strToBase64(String input) {
        Base64.Encoder urlEncoder = Base64.getUrlEncoder();
        byte[] encode = urlEncoder.encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encode, StandardCharsets.UTF_8);
    }

    /**
     * base64解码成字符串
     *
     * @param base64
     * @return
     */
    public static String base64ToStr(String base64) {
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] decode = urlDecoder.decode(base64.getBytes(StandardCharsets.UTF_8));
        return new String(decode, StandardCharsets.UTF_8);
    }

    /**
     * 文件转base64编码
     *
     * @param inputFile
     * @return
     */
    public static String fileToBase64(File inputFile) {
        //读文件
        try {
            if (!(inputFile.exists() && inputFile.canRead())) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            byte[] buf = new byte[fileInputStream.available()];
            buf = fileInputStream.readAllBytes();
            fileInputStream.close();

            //输出
            byte[] encode = Base64.getUrlEncoder().encode(buf);
            return new String(encode, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void base64ToFile(String base64Str, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                boolean createSuc = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] decode = Base64.getUrlDecoder().decode(base64Str.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.write(decode);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
