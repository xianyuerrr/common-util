package com.xianyue.common.auth.service.helper;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CredentialStorage {
    String getPasswordByAppId(@NotBlank String appId);
}
