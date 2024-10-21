package com.z.utils;

import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * ǩ���㷨
 * ������ ǩ�� �� �ǶԳ��㷨
 * ��ǰ����MD5��RSA�㷨
 * ˽Կ���ܣ���Կ����
 */
public class SignUtils {
    public static String PRIVATE_KEY = "privateKey";
    public static String PUBLIC_KEY = "publicKey";

    //��˽��Կ
    public static Map<String, Key> setKeyPair(String seed) {
        //ָ����Կ����RSA�㷨
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
     * MD5����ժҪ�㷨,��content����ɶ���ǩ��
     *
     * @param content
     * @return ����Base64������ַ���
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
     * ��content����ǩ��
     *
     * @param content
     * @param priKey
     * @return
     */
    public static String sign(String content, PrivateKey priKey) {
        try {
            //ʹ��MD5����ժҪ��RSA���зǶԳƼ���
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes());
            //��contentǩ��
            byte[] sign = signature.sign();
            //���base64�����ǩ��
            return Base64.getUrlEncoder().encodeToString(sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * ��ǩ�����Լ������ժҪ��ǩ�����ܳ�����ժҪ���жԱ�
     *
     * @param content       ��������
     * @param base64SignStr ǩ���ַ���
     * @param pubKey        ��Կ
     * @return
     */
    public static boolean verify(String content, String base64SignStr, PublicKey pubKey) {
        try {
            //ʹ��MD5���н�ǩ
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initVerify(pubKey);
            //���뵱ǰ�û����е�����
            signature.update(content.getBytes());
            //��֤ǩ��
            return signature.verify(Base64.getUrlDecoder().decode(base64SignStr));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
