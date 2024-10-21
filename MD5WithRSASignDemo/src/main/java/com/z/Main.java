package com.z;

import com.z.utils.SignUtils;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String keySeed = "this is a sign seed";
        //生成密钥
        Map<String, Key> keyMap = SignUtils.setKeyPair(keySeed);
        String content = "this is a test String ready to sign!";
        String fakeContent = "this is a fake test String ready to sign!";
        //私钥签名(base64)
        String sign = SignUtils.sign(content, (PrivateKey) keyMap.get(SignUtils.PRIVATE_KEY));
        System.out.println("签名内容:" + sign);
        //公钥验签:
        boolean verify = SignUtils.verify(fakeContent, sign, (PublicKey) keyMap.get(SignUtils.PUBLIC_KEY));
        System.out.println("验签结果:" + verify);
    }
}