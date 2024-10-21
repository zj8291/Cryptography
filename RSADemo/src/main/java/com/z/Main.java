package com.z;

import com.z.utils.RSAUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //生成种子
        String keySeed = "RSA_KEY_SEED_" + new Date().getTime();
        //生成公钥和私钥
        Map<String, Key> keyMap = RSAUtils.setKeyPair(keySeed);
        System.out.println(keyMap.toString());
        String content = "this is my test text,Please encrypt me!";
        System.out.println("原文:" + content);
        //公钥加密
        String encrypt = RSAUtils.encryptWithPublicKey(content, keyMap.get(RSAUtils.PUBLIC_KEY));
        System.out.println("RSA密文:" + encrypt);
        //私钥解密
        String decrypt = RSAUtils.decryptWithPrivateKey(encrypt, keyMap.get(RSAUtils.PRIMARY_KEY));
        System.out.println("RSA明文:" + decrypt);
    }
}