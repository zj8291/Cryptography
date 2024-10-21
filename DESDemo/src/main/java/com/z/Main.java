package com.z;

import com.z.utils.DESUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {

        final String keyStr = "12345678";
        String input = "Hello World!Hello World!Hello World!Hello World!Hello World!";  //明文
        //生成56位的key,TODO:注意:每次执行generateKey方法生成的key每次都不一样,即使输入字符串是一样的
        Key key = DESUtils.setKey(keyStr);

        System.out.println("原文:" + input);
        String encrypt = DESUtils.encrypt(input, key);
        System.out.println("密文:" + encrypt);
        String decrypt = DESUtils.decrypt(encrypt, key);
        System.out.println("解密后:" + decrypt);
    }
}