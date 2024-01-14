package com.xianyue.common.auth.service.impl;

import com.xianyue.common.auth.enums.ErrorCode;
import com.xianyue.common.auth.service.ApiAuthencator;
import com.xianyue.common.auth.service.helper.CredentialStorage;
import com.xianyue.common.auth.vo.ApiRequest;
import com.xianyue.common.auth.vo.AuthToken;
import com.xianyue.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultApiAuthencatorImpl implements ApiAuthencator {
    @Autowired
    private CredentialStorage credentialStorage;

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.creatFromFullUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String token = apiRequest.token();
        long timestamp = apiRequest.timestamp();

        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        if (clientAuthToken.isExpired()) {
            throw new CommonException(ErrorCode.TOKEN_EXPIRED.getErrorCode());
        }

        String baseUrl = apiRequest.baseUrl();
        String appId = apiRequest.appId();

        String password = credentialStorage.getPasswordByAppId(appId);
        AuthToken serverAuthToken = AuthToken.creat(baseUrl, appId, password, timestamp);
        if (!serverAuthToken.match(clientAuthToken)) {
            throw new CommonException(ErrorCode.VERIFICATION_FAIL.getErrorCode());
        }

    }
}
