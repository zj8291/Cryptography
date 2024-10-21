package com.z.utils;

import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
    static final String PUBLIC_KEY = "public_key";
    static final String PRIMARY_KEY = "primary_key";

    /**
     * 生成RSA密钥对(base64格式)
     *
     * @return
     */
    public static Map<String, String> setKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //指定密钥长度为2048
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey aPrivate = keyPair.getPrivate();
            PublicKey aPublic = keyPair.getPublic();
            Map<String, String> keyMap = new HashMap<>();
            keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(aPublic.getEncoded()));
            keyMap.put(PRIMARY_KEY, Base64.getEncoder().encodeToString(aPrivate.getEncoded()));
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
