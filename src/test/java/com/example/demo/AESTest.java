package com.example.demo;

import com.example.demo.utils.AESUtil;
import com.example.demo.utils.CustomTokenUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.HexFormat;

public class AESTest {
    @Disabled
    @Test
    public void aesTest() throws Exception {
        // 生成密钥
        byte[] key = AESUtil.generateKey();
        System.out.println("生成的密钥64: " + Base64.getUrlEncoder().withoutPadding().encodeToString(key));
        System.out.println("生成的密钥16: " + HexFormat.of().withUpperCase().formatHex(key));

        // 明文
        String plainText = "{\"username\": \"Zhangsan\", \"age\": 30, \"city\"\": \"BeiJing\", \"email\": \"Zhangsan@example.com\"}";
        System.out.println("加密前的明文: " + plainText);

        // 加密
        String encrypted = AESUtil.encryptAndEncode(key, plainText, 0);
        String encrypted16 = AESUtil.encryptAndEncode(key, plainText, 1);
        System.out.println("加密后的数据64: " + encrypted);
        System.out.println("加密后的数据16: " + encrypted16);

        // 解密
        String decrypted = AESUtil.decryptAndDecode(key, encrypted, 0);
        System.out.println("解密后的数据64: " + decrypted);
        System.out.println("解密后的数据16: " + AESUtil.decryptAndDecode(key, encrypted16, 1));
    }

    @Disabled
    @Test
    public void tokenTest() throws Exception {
        String key = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6"; // 32字节的密钥
        CustomTokenUtil customTokenUtil = new CustomTokenUtil(key.getBytes());
        String token = customTokenUtil.generateToken("12345678901234567890123456789012", 60);
        System.out.println("生成的Token: " + token);
        System.out.println("source input: " + customTokenUtil.decodeToken(token));
    }

    @Disabled
    @Test
    public void checkTokenTest() throws Exception {
        String key = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6"; // 32字节的密钥
        CustomTokenUtil customTokenUtil = new CustomTokenUtil(key.getBytes());
        System.out.println("source input: " + customTokenUtil.decodeToken("0.HckzMGsE8mApZkkbI7J5IQ:z2VqN5jG9wb2E2zptYhLTNC3S6hxTALqqUF3mC7BBu9gk0BgL7qtZ4OuFFmRg4ENczbrm6liVrWFWb2zt4nvCw"));
    }
}
