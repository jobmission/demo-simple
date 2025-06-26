package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HMACTest {
    // 支持算法: HmacSHA256/HmacMD5/HmacSHA1
    private static final String ALGORITHM = "HmacSHA256";

    @Disabled
    @Test
    public void hmacTest() throws Exception {
        String message = "Hello HMAC";
        String secretKey = "mySecretKey123";

        // 生成HMAC签名
        String signature = generateHmac(message, secretKey);
        System.out.println("HMAC签名: " + signature);

        // 验证签名
        boolean isValid = verifyHmac(message, secretKey, signature);
        System.out.println("验证结果: " + isValid);
    }

    public static String generateHmac(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secretKey);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public static boolean verifyHmac(String data, String key, String signature) throws NoSuchAlgorithmException, InvalidKeyException {
        String calculatedSignature = generateHmac(data, key);
        return calculatedSignature.equals(signature);
    }
}
