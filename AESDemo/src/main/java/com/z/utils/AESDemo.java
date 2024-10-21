package com.z.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * AES属于分组加密，算法明文长度固定为128位（单位是比特bit，1bit就是1位，128位等于16字节）
 * <p>
 * 而密钥长度可以是128、192、256位
 * <p>
 * 当密钥为128位时，需要循环10轮完成加密，在此基础上，密钥每增加64位，就要多循环2轮（即192位密钥循环12轮，256位密钥循环14轮）
 */
public class AESDemo {
    /**
     * 生成key
     *
     * @param key
     * @return
     */
    public static Key setKey(String key) {
        try {
            //对输入进行SHA-256处理,将长度整合为256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(key.getBytes());
            //生成AES密钥
            return new SecretKeySpec(digest, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES加密
     *
     * @param input
     * @param key
     * @return
     */
    public static String encrypt(String input, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(input.getBytes());
            return Base64.getUrlEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES解密
     *
     * @param input base64编码的密文
     * @param key
     * @return
     */
    public static String decrypt(String input, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(Base64.getUrlDecoder().decode(input));
            return new String(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
