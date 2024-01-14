package com.xianyue.common.auth.interceptor;

import com.xianyue.common.auth.service.ApiAuthencator;
import com.xianyue.common.auth.vo.ApiRequest;
import com.xianyue.common.context.spi.XianYueContextUtils;
import com.xianyue.common.context.vo.XianYueContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * @Title: XianYueContextInterceptor
 * @Package: com.xianyue.common.context.interceptor
 * @Description: 应用程序上下文获取拦截器
 * @Author: xianyue
 * @Date: 2024/1/14 19:55
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private ApiAuthencator apiAuthencator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        XianYueContext context = XianYueContextUtils.getContext();
        ApiRequest apiRequest = new ApiRequest(context.getBaseUrl(), context.getToken(), context.getAppId(), Optional.ofNullable(context.getCreateTime()).orElse(0L));
        apiAuthencator.auth(apiRequest);
        return true;
    }
}
