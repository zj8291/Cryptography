package com.z;

import com.z.utils.AESDemo;

import java.security.Key;

public class Main {
    public static void main(String[] args) {
        String keyStr = "this is a AES key...";
        String content = "This link is intended solely for testing your MBTI. The content beyond this point is not under our liability.";
        System.out.println("明文:" + content);
        //生成密钥
        Key key = AESDemo.setKey(keyStr);
        String encrypt = AESDemo.encrypt(content, key);
        System.out.println("密文:" + encrypt);
        String decrypt = AESDemo.decrypt(encrypt, key);
        System.out.println("解密后:" + decrypt);
    }
}