package com.xianyue.common.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Title: AuthKeyEnum
 * @Package: com.xianyue.common.auth.enums
 * @Description: 鉴权所需 Key 的枚举
 * @Author: xianyue
 * @Date: 2024/1/18 22:23
 */
@Getter
@AllArgsConstructor
public enum AuthKeyEnum {
    USER_ID("UserId"),
    APP_ID("AppId"),
    TOKEN("Token"),
    CREATE_TIME("CreatTime"),
    ;

    private final String keyName;
}
