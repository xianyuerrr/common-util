package com.xianyue.common.auth.service.helper;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * 密钥获取服务
 */
@Validated
public interface CredentialStorage {
    String getPasswordByAccount(@NotBlank String appId);
}
