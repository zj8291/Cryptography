package com.z.utils;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * DES对称加密
 * DES使用56位的密钥和64位的明文块进行加密。DES算法的分组大小是64位，因此，如果需要加密的明文长度不足64位，需要进行填充；如果明文长度超过64位，则需要使用分组模式进行分组加密。
 * 虽然DES算法的分组大小是64位，但是由于DES算法的密钥长度只有56位，因此DES算法存在着弱点，容易受到暴力破解和差分攻击等攻击手段的威胁。因此，在实际应用中，DES算法已经不再被广泛使用，而被更加安全的算法所取代，如AES算法等。
 * 尽管DES已经被取代，但它在密码学的历史上仍然具有重要意义。通过DES可以帮助我们了解对称密钥加密算法的基本概念和运作原理。
 */
public class DESUtils {

    /**
     * 生成DES密钥
     *
     * @param keyStr 生成字符串
     * @return
     */
    public static Key setKey(String keyStr) {
        Key key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            //根据输入字符串生成key，保证每次生成的key可控
            keyGenerator.init(new SecureRandom(keyStr.getBytes()));
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    /**
     * DES加密
     *
     * @param input 明文
     * @param key   密钥
     * @return
     */
    public static String encrypt(String input, Key key) {
        //获取密钥
        try {
            Cipher cipher = Cipher.getInstance("DES");
            //设置工作模式和密钥
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //对utf-8编码的明文进行加密
            byte[] bytes = cipher.doFinal(input.getBytes());
            return Base64.getUrlEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DES解密
     *
     * @param input 密文
     * @param key   密钥
     * @return
     */
    public static String decrypt(String input, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            //设置工作模式和密钥
            cipher.init(Cipher.DECRYPT_MODE, key);
            //对base64编码的明文进行解密
            byte[] bytes = cipher.doFinal(Base64.getUrlDecoder().decode(input.getBytes()));
            return new String(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
