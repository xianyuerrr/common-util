package com.xianyue.common.auth.vo;


import lombok.Getter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Getter
public class AuthToken {
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 60 * 1000;

    private final String token;
    private final long createTime;
    private long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL;


    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    public static AuthToken creat(String baseUrl, String appId, String password, long creatTime) {
        String token = generateToken(baseUrl, appId, creatTime, password);
        return new AuthToken(token, creatTime);
    }

    private static String generateToken(String baseUrl, String appId, long creatTime, String password) {
        return hmacSha1(baseUrl + appId + creatTime, password);
    }

    // md5 加密
    private static String hmacSha1(String str, String password) {
        try {
            // Get a hmac_sha1 key from the raw key bytes
            byte[] keyBytes = password.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get a hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(str.getBytes());

            byte[] result = Base64.getEncoder().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        long time = System.currentTimeMillis();
        return !(time >= createTime && time <= createTime + expiredTimeInterval);
    }

    public boolean match(AuthToken authToken) {
        return this.token.equals(authToken.getToken());
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", createTime=" + createTime +
                ", expiredTimeInterval=" + expiredTimeInterval +
                '}';
    }
}
