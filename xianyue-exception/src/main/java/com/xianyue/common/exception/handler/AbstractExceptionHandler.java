package com.xianyue.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Title: AbstractExceptionHandler
 * @Package: com.xianyue.common.exception.handler
 * @Description: 异常处理虚类
 * @Author: xianyue
 * @Date: 2023/6/17 13:30
 */
@Slf4j
public abstract class AbstractExceptionHandler implements ExceptionHandler {
    @Value("${xianyue.exception.default.code: xianyue-001}")
    protected String defaultErrCode;

    protected void logException(Exception exception) {
        log.error("Exception: message: {}", exception.getMessage(), exception);
    }
}
