package com.xianyue.common.auth.service;

import com.xianyue.common.auth.vo.ApiRequest;
import com.xianyue.common.auth.vo.AuthToken;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Api校验器
 */
@Validated
public interface ApiAuthencator {
    /**
     * 根据服务地址鉴权，不通过时抛出异常
     *
     * @param url 服务地址
     */
    void auth(@NotBlank String url);

    /**
     * 根据服务地址鉴权，不通过时抛出异常
     *
     * @param apiRequest Api 请求
     */
    void auth(@NotNull ApiRequest apiRequest);

    /**
     * 账户登录，成功时返回 Token，作为 Client 临时访问的令牌
     *
     * @param account  账户
     * @param password 密码
     * @param baseUrl  服务，认证通过后可访问相同 baseUrl 下的所有服务
     * @return 鉴权 Token
     */
    AuthToken login(@NotBlank String account, @NotNull String password, @NotBlank String baseUrl);

    /**
     * 重新认证，生成 Token
     *
     * @param account 账户
     * @param baseUrl 服务
     * @return 鉴权 Token
     */
    AuthToken reAuth(@NotBlank String account, @NotBlank String baseUrl);
}
