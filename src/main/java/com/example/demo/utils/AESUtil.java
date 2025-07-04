package com.example.demo.utils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

public class AESUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;
    private static final int KEY_LENGTH = 256;
    private static final SecureRandom secureRandom = new SecureRandom(BigInteger.valueOf(System.currentTimeMillis()).toByteArray());
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    /**
     * 生成256位的AES密钥
     *
     * @return 生成的密钥字节数组
     * @throws Exception 如果密钥生成失败
     */
    public static byte[] generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_LENGTH);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 生成密钥（返回Base64或16进制字符串）
     *
     * @param bytesEncodeType 0: Base64编码，1: 16进制编码
     * @return 生成的密钥字符串
     * @throws Exception 如果密钥生成失败
     */
    public static String generateKey(int bytesEncodeType) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_LENGTH);
        SecretKey secretKey = keyGen.generateKey();
        if (bytesEncodeType == 0) {
            return encoder.encodeToString(secretKey.getEncoded());
        } else {
            return HexFormat.of().formatHex(secretKey.getEncoded());
        }
    }

    /**
     * 加密（返回16进制或者Base64字符串，格式为IV+密文）
     *
     * @param keyBytes        32字节的密钥
     * @param plainText       明文字符串
     * @param bytesEncodeType 0: Base64编码，1: 16进制编码
     * @return 加密后的字符串
     * @throws Exception 如果密钥长度不正确或加密失败
     */
    public static String encrypt(byte[] keyBytes, String plainText, int bytesEncodeType) throws Exception {
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 256 bits (32 bytes) long.");
        }
        byte[] ivBytes = generateIV();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(keyBytes, "AES"),
                new IvParameterSpec(ivBytes));

        byte[] ciphertext = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        if (bytesEncodeType == 0) {
            return encoder.encodeToString(ivBytes) + ":" + encoder.encodeToString(ciphertext);
        } else {
            return HexFormat.of().formatHex(ivBytes) + ":" + HexFormat.of().formatHex(ciphertext);
        }
    }

    /**
     * 解密（输入16进制或者Base64字符串）
     *
     * @param keyBytes        32字节的密钥
     * @param encryptedText   加密后的字符串
     * @param bytesEncodeType 0: Base64编码，1: 16进制编码
     * @return 解密后的明文字符串
     * @throws Exception 如果密钥长度不正确或解密失败
     */
    public static String decrypt(byte[] keyBytes, String encryptedText, int bytesEncodeType) throws Exception {
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 256 bits (32 bytes) long.");
        }
        String[] parts = encryptedText.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid encrypted data format.");
        }
        if (bytesEncodeType == 0) {
            byte[] ivBytes = decoder.decode(parts[0]);
            byte[] ciphertext = decoder.decode(parts[1]);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(ivBytes));
            return new String(cipher.doFinal(ciphertext));
        } else {
            byte[] ivBytes = HexFormat.of().parseHex(parts[0]);
            byte[] ciphertext = HexFormat.of().parseHex(parts[1]);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(keyBytes, "AES"),
                    new IvParameterSpec(ivBytes));

            return new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
        }
    }

    private static byte[] generateIV() {
        byte[] iv = new byte[IV_LENGTH];
        secureRandom.nextBytes(iv);
        return iv;
    }
}

