package com.xianyue.common.auth.service;

import com.xianyue.common.auth.vo.ApiRequest;

public interface ApiAuthencator {
    void auth(String url);

    void auth(ApiRequest apiRequest);
}
