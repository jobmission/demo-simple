package com.example.demo.utils;

public class CustomTokenUtil {
    private final byte[] secretKey;
    int bytesEncodeType = 0; // 默认使用Base64编码

    public CustomTokenUtil(byte[] secretKey) {
        if (secretKey == null || secretKey.length != 32) {
            throw new IllegalArgumentException("Secret key must be a 32-byte array");
        }
        this.secretKey = secretKey;
    }

    /**
     * 生成Token，默认过期时间为24小时，format: 0/1.xxx:yyy
     *
     * @param input              输入字符串
     * @param tokenExpireSeconds 过期时间（秒），小于等于0时默认为24小时
     * @return 生成的Token字符串
     * @throws Exception 如果加密失败
     */
    public String generateToken(String input, int tokenExpireSeconds) throws Exception {

        long currentTimeMillis = System.currentTimeMillis();
        long expireMillis;
        if (tokenExpireSeconds <= 0) {
            expireMillis = currentTimeMillis + 1000L * 60 * 60 * 24; // 默认设置为24小时后过期
        } else {
            expireMillis = currentTimeMillis + 1000L * tokenExpireSeconds; // 设置为指定的过期时间
        }
        String encodedInput = AESUtil.encryptAndEncode(secretKey, input + "@" + expireMillis + ":" + currentTimeMillis, bytesEncodeType);
        return bytesEncodeType + "." + encodedInput;
    }

    /**
     * 解码Token，验证过期时间并返回原始字符串。 format: 0/1.xxx:yyy
     *
     * @param encryptedToken 加密的Token字符串
     * @return 解码后的原始字符串
     * @throws Exception 如果解密失败或Token格式不正确
     */
    public String decodeToken(String encryptedToken) throws Exception {
        if (encryptedToken == null || encryptedToken.isBlank()) {
            throw new IllegalArgumentException("Invalid token format 1");
        }
        long count = encryptedToken.chars()
            .filter(c -> c == '.')
            .count();

        if (count != 1) {
            throw new IllegalArgumentException("Invalid token format 2");
        }
        count = encryptedToken.chars()
            .filter(c -> c == ':')
            .count();
        if (count != 1) {
            throw new IllegalArgumentException("Invalid token format 3");
        }
        String decrypted = AESUtil.decryptAndDecode(secretKey, encryptedToken.replace(bytesEncodeType + ".", ""), bytesEncodeType);
        int lastIndexOf = decrypted.lastIndexOf('@');
        if (lastIndexOf == -1) {
            throw new IllegalArgumentException("Invalid token format 4");
        }
        String[] timestampParts = decrypted.substring(lastIndexOf + 1).split(":");
        if (timestampParts.length != 2) {
            throw new IllegalArgumentException("Invalid token format 5");
        }
        long timestamp = Long.parseLong(timestampParts[0]);
        if (timestamp < System.currentTimeMillis()) {
            throw new IllegalArgumentException("Token has expired at " + timestamp + ", issued at " + timestampParts[1]);
        }
        return decrypted.substring(0, lastIndexOf);
    }
}
