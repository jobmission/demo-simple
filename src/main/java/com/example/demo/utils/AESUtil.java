package com.example.demo.utils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
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
     * 加密（返回16进制或者Base64字符串，格式为IV:密文）
     *
     * @param keyBytes        32字节的密钥
     * @param plainText       明文字符串
     * @param bytesEncodeType 0: Base64编码，1: 16进制编码
     * @return 加密后的字符串
     * @throws Exception 如果密钥长度不正确或加密失败
     */
    public static String encryptAndEncode(byte[] keyBytes, String plainText, int bytesEncodeType) throws Exception {
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
     * 解密（输入16进制或者Base64字符串，格式为IV:密文）
     *
     * @param keyBytes        32字节的密钥
     * @param encryptedText   加密后的字符串
     * @param bytesEncodeType 0: Base64编码，1: 16进制编码
     * @return 解密后的明文字符串
     * @throws Exception 如果密钥长度不正确或解密失败
     */
    public static String decryptAndDecode(byte[] keyBytes, String encryptedText, int bytesEncodeType) throws Exception {
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

    /**
     * 加密方法
     *
     * @param keyBytes  32字节的密钥
     * @param plaintext 明文字符串
     * @return 加密后的Base64字符串，包含IV和密文
     * @throws Exception 如果密钥长度不正确或加密失败
     */
    public static String encrypt(byte[] keyBytes, String plaintext) throws Exception {
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 256 bits (32 bytes) long.");
        }
        // 创建密钥对象
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // 生成随机IV（16字节）
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 初始化加密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // 执行加密
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        // 合并IV和密文
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 解密方法
     *
     * @param keyBytes   32字节的密钥
     * @param ciphertext 加密后的Base64字符串，包含IV和密文
     * @return 解密后的明文字符串
     * @throws Exception 如果密钥长度不正确或解密失败
     */
    public static String decrypt(byte[] keyBytes, String ciphertext) throws Exception {
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 256 bits (32 bytes) long.");
        }
        // 解码Base64字符串
        byte[] combined = Base64.getDecoder().decode(ciphertext);

        // 提取IV和密文
        byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH);
        byte[] encryptedBytes = Arrays.copyOfRange(combined, IV_LENGTH, combined.length);

        // 创建密钥对象
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // 初始化解密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        // 执行解密
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 生成随机IV
     *
     * @return 生成的IV字节数组
     */
    private static byte[] generateIV() {
        byte[] iv = new byte[IV_LENGTH];
        secureRandom.nextBytes(iv);
        return iv;
    }
}

