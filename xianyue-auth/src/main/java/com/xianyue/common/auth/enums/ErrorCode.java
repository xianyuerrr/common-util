package com.xianyue.common.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Title: ErrorCode
 * @Package: com.xianyue.common.auth
 * @Description:
 * @Author: xianyue
 * @Date: 2024/1/14 15:12
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    TOKEN_EXPIRED("auth-001", "Token is expired."),
    VERIFICATION_FAIL("auth-002", "Token verification failed.");

    private String errorCode;

    private String errMsg;
}
