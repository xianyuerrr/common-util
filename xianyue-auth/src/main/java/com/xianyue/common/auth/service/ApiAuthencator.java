package com.xianyue.common.auth.service;

import com.xianyue.common.auth.vo.ApiRequest;
import com.xianyue.common.auth.vo.AuthToken;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ApiAuthencator {
    void auth(@NotBlank String url);

    void auth(@NotNull ApiRequest apiRequest);

    AuthToken login(@NotBlank String account, @NotNull String password, @NotBlank String baseUrl);

    AuthToken reAuth(@NotBlank String account, @NotBlank String baseUrl);
}
