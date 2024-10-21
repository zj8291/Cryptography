package com.z.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Utils {

    public static String sha256ToString(String input) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1, digest);
        //转换为16进制
        return bigInteger.toString(16);
    }

}
