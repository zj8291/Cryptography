package com.z.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
    public static final String PUBLIC_KEY = "public_key";
    public static final String PRIMARY_KEY = "primary_key";

    /**
     * 生成RSA密钥对
     *
     * @return
     */
    public static Map<String, Key> setKeyPair(String keySeed) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //指定密钥长度为2048
            keyPairGenerator.initialize(2048, new SecureRandom(keySeed.getBytes()));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey aPrivate = keyPair.getPrivate();
            PublicKey aPublic = keyPair.getPublic();
            Map<String, Key> keyMap = new HashMap<>();
            keyMap.put(PUBLIC_KEY, aPublic);
            keyMap.put(PRIMARY_KEY, aPrivate);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥加密
     *
     * @param input
     * @param pubKey
     * @return 返回base64编码的密文
     */
    public static String encryptWithPublicKey(String input, Key pubKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.PUBLIC_KEY, pubKey);
            byte[] bytes = cipher.doFinal(input.getBytes());
            return Base64.getUrlEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     *
     * @param input  输入base64编码的密文
     * @param priKey
     * @return
     */
    public static String decryptWithPrivateKey(String input, Key priKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.PRIVATE_KEY, priKey);
            byte[] bytes = cipher.doFinal(Base64.getUrlDecoder().decode(input));
            return new String(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
