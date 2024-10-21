package com.z.utils;

import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名算法
 * 依赖于 签名 和 非对称算法
 * 当前采用MD5和RSA算法
 * 私钥加密，公钥解密
 */
public class SignUtils {
    public static String PRIVATE_KEY = "privateKey";
    public static String PUBLIC_KEY = "publicKey";

    //公私密钥
    public static Map<String, Key> setKeyPair(String seed) {
        //指定密钥用于RSA算法
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, new SecureRandom(seed.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Map<String, Key> keyMap = new HashMap<>();
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());
        return keyMap;
    }

    /**
     * MD5进行摘要算法,将content计算成定长签名
     *
     * @param content
     * @return 返回Base64编码的字符串
     */
    public static String digest(String content) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(content.getBytes());
            return Base64.getUrlEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对content进行签名
     *
     * @param content
     * @param priKey
     * @return
     */
    public static String sign(String content, PrivateKey priKey) {
        try {
            //使用MD5进行摘要，RSA进行非对称加密
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes());
            //对content签名
            byte[] sign = signature.sign();
            //输出base64编码的签名
            return Base64.getUrlEncoder().encodeToString(sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 验签，将自己计算的摘要和签名解密出来的摘要进行对比
     *
     * @param content       明文内容
     * @param base64SignStr 签名字符串
     * @param pubKey        公钥
     * @return
     */
    public static boolean verify(String content, String base64SignStr, PublicKey pubKey) {
        try {
            //使用MD5进行解签
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initVerify(pubKey);
            //传入当前用户持有的数据
            signature.update(content.getBytes());
            //验证签名
            return signature.verify(Base64.getUrlDecoder().decode(base64SignStr));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
