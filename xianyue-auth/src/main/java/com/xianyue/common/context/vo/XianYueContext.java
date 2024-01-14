package com.xianyue.common.context.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: XianYueContext
 * @Package: com.xianyue.common.context.vo
 * @Description: 请求上下文
 * @Author: xianyue
 * @Date: 2024/1/14 16:40
 */
@Data
public class XianYueContext {
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 应用Id
     */
    private String appId;

    /**
     * token
     */
    private String token;

    /**
     * token生成时间
     */
    private Long createTime;

    /**
     * 基础url
     */
    private String baseUrl;

    /**
     * 链路追踪Id
     */
    private String traceId;

    /**
     * 语言环境
     */
    private String lang;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 上下文扩展内容，比如当前语言等
     */
    private Map<String, Object> extMap;

    public <T> void setExtVal(String key, T obj) {
        if (this.extMap == null) {
            this.extMap = new HashMap<>();
        }
        this.extMap.put(key, obj);
    }

    public <T> T getExtVal(String key) {
        if (this.extMap == null) {
            return null;
        }
        return (T) this.extMap.get(key);
    }
}
