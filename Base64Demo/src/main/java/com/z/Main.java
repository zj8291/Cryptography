package com.z;

import com.z.utils.Base64Utils;

import java.io.File;
import java.util.Date;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String s = Base64Utils.strToBase64("Hello world!");
        System.out.println("Base64:" + s);
        System.out.println("s:" + Base64Utils.base64ToStr(s));

        //输出的仅仅为文件编码本身，还需要加上一些说明信息才可以被识别为对应的文件
        String imgBase64 = Base64Utils.fileToBase64(new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("img.png")).getFile()));

        System.out.println(imgBase64);

        Base64Utils.base64ToFile(imgBase64, ClassLoader.getSystemResource("").getPath() + "/output/base64_image_" + new Date().getTime() + ".png");
    }
}