package com.xianyue.common.auth.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * api请求定义
 *
 * @param baseUrl   基础url
 * @param token     token
 * @param appId     应用id
 * @param timestamp 时间戳
 */
public record ApiRequest(String baseUrl, String token, String appId, long timestamp) {
    public static ApiRequest creatFromFullUrl(String url) {
        if (url == null || "".equals(url)) {
            return null;
        }
        int idx = url.indexOf("?");
        String baseUrl = url.substring(0, idx);
        String paramsStr = url.substring(idx + 1);
        Map<String, String> params = new HashMap<>();
        for (String param : paramsStr.split("&")) {
            int splitIdx = param.indexOf("=");
            String key = param.substring(0, splitIdx);
            String val = param.substring(splitIdx + 1);
            params.put(key, val);
        }
        String token = params.getOrDefault("token", "");
        String appId = params.getOrDefault("appId", "");
        long timestamp = Long.parseLong(params.getOrDefault("timestamp", ""));
        return new ApiRequest(baseUrl, token, appId, timestamp);
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "baseUrl='" + baseUrl + '\'' +
                ", token='" + token + '\'' +
                ", appId='" + appId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
