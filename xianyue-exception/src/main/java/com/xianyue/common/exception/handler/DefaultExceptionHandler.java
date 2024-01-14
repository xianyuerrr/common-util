package com.xianyue.common.exception.handler;

import com.xianyue.common.core.response.ApiResponse;
import com.xianyue.common.core.response.Error;
import com.xianyue.common.exception.CommonException;
import com.xianyue.common.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

/**
 * @Title: DefaultExceptionHandler
 * @Package: com.xianyue.common.exception.handler
 * @Description: 默认异常处理类
 * @Author: xianyue
 * @Date: 2023/6/17 13:38
 */
@Component
public class DefaultExceptionHandler<T> extends AbstractExceptionHandler {
    @Override
    public boolean isSupport(Exception exception) {
        return true;
    }

    @Override
    public ApiResponse<T> handleException(Exception exception) {
        ApiResponse<T> response = null;
        if (exception instanceof CommonException commonException) {
            if (Objects.isNull(commonException.getData())) {
                response = ApiResponse.fail(commonException.getErrorList());
            } else {
                response = ApiResponse.partialSuccess((T) commonException.getData(), commonException.getErrorList());
            }
        } else {
            Error error = new Error(ExceptionUtils.getDefaultErrorCode(), null, exception.getMessage());
            response = ApiResponse.fail(Collections.singletonList(error));
        }
        this.logException(exception);
        return response;
    }

    @Override
    public int order() {
        return 10;
    }
}
