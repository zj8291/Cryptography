package com.z;

import com.z.utils.MD5Utils;
import com.z.utils.SHA256Utils;

public class Main {
    public static void main(String[] args) {
        //32位MD5加密
        System.out.println(MD5Utils.strToMd5("Hello world!"));
        //16位MD5加密
        System.out.println(MD5Utils.strToMd5("Hello world!").substring(8, 24));
        //SHA-256加密
        System.out.println(SHA256Utils.sha256ToString("Hello world!"));
    }
}