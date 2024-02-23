package com.xianyue.common.auth.service.impl;

import com.xianyue.common.auth.enums.ErrorCode;
import com.xianyue.common.auth.service.ApiAuthencator;
import com.xianyue.common.auth.service.helper.CredentialStorage;
import com.xianyue.common.auth.vo.ApiRequest;
import com.xianyue.common.auth.vo.AuthToken;
import com.xianyue.common.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 默认Api校验器
 */
@Slf4j
@AllArgsConstructor
public class DefaultApiAuthencatorImpl implements ApiAuthencator {
    private CredentialStorage credentialStorage;

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.creatFromFullUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String token = apiRequest.token();
        // TODO 接管创建时间，1.存在cookie，2. redis
        long timestamp = apiRequest.timestamp();

        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        if (clientAuthToken.isExpired()) {
            throw new CommonException(ErrorCode.TOKEN_EXPIRED.getErrorCode());
        }

        String baseUrl = apiRequest.baseUrl();
        String account = apiRequest.account();

        String password = credentialStorage.getPasswordByAccount(account);
        AuthToken serverAuthToken = AuthToken.creat(baseUrl, account, password, timestamp);
        if (!serverAuthToken.match(clientAuthToken)) {
            throw new CommonException(ErrorCode.VERIFICATION_FAIL.getErrorCode());
        }

    }

    @Override
    public AuthToken login(String account, String password, String baseUrl) {
        String realPassword = credentialStorage.getPasswordByAccount(account);
        if (realPassword == null) {
            throw new CommonException("");
        }
        if (!realPassword.equals(password)) {
            throw new CommonException("");
        }
        return AuthToken.creat(baseUrl, account, password, new Date().getTime());
    }

    @Override
    public AuthToken reAuth(String account, String baseUrl) {
        String password = credentialStorage.getPasswordByAccount(account);
        long timestamp = new Date().getTime();
        return AuthToken.creat(baseUrl, account, password, timestamp);
    }
}
