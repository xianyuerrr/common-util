package com.xianyue.common.auth.interceptor;

import com.xianyue.common.auth.enums.AuthKeyEnum;
import com.xianyue.common.auth.service.ApiAuthencator;
import com.xianyue.common.auth.vo.ApiRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Map<String, String> map = Arrays.stream(request.getCookies()).collect(Collectors.toMap(Cookie::getName,
                Cookie::getValue));
        String baseUrl = request.getScheme() + request.getServerName();
        String appId = map.get(AuthKeyEnum.APP_ID.getKeyName());
        String userId = map.get(AuthKeyEnum.USER_ID.getKeyName());
        String token = map.get(AuthKeyEnum.TOKEN.getKeyName());
        String createTime = Optional.ofNullable(map.get(AuthKeyEnum.CREATE_TIME.getKeyName())).orElse("0");

        if (Strings.isNotBlank(appId)) {
            request.setAttribute(AuthKeyEnum.APP_ID.getKeyName(), appId);
        }
        if (Strings.isNotBlank(userId)) {
            request.setAttribute(AuthKeyEnum.USER_ID.getKeyName(), appId);
        }

        ApiRequest apiRequest = new ApiRequest(baseUrl, token, Optional.ofNullable(appId).orElse(userId),
                Long.parseLong(createTime));
        apiAuthencator.auth(apiRequest);
        return true;
    }
}
