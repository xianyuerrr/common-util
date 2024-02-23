package com.xianyue.common.auth.interfaces;

import com.xianyue.common.auth.enums.AuthKeyEnum;
import com.xianyue.common.auth.service.ApiAuthencator;
import com.xianyue.common.auth.vo.AuthToken;
import com.xianyue.common.core.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 鉴权模块对外暴露的服务
 *
 * @Title: AuthController
 * @Package: com.xianyue.common.auth.interfaces
 * @Description: 权限对外接口，提供登录、登出功能
 * @Author: xianyue
 * @Date: 2024/1/16 23:57
 */
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private ApiAuthencator apiAuthencator;

    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestParam("account") String account, @RequestBody String password,
                                   HttpServletRequest request, HttpServletResponse response) {
        String baseUrl = request.getScheme() + request.getServerName();
        AuthToken authToken = apiAuthencator.login(account, password, baseUrl);
        Cookie accountCookie = new Cookie(AuthKeyEnum.APP_ID.getKeyName(), account);
        Cookie tokenCookie = new Cookie(AuthKeyEnum.TOKEN.getKeyName(), authToken.getToken());
        Cookie createTimeCookie = new Cookie(AuthKeyEnum.CREATE_TIME.getKeyName(),
                String.valueOf(authToken.getCreateTime()));

        response.addCookie(accountCookie);
        response.addCookie(tokenCookie);
        response.addCookie(createTimeCookie);
        return ApiResponse.success(null);
    }

    @GetMapping("/reAuth")
    public ApiResponse<Void> reAuth(HttpServletRequest request, HttpServletResponse response) {
        String baseUrl = request.getScheme() + request.getServerName();
        String account = null;
        Object appId = request.getAttribute(AuthKeyEnum.APP_ID.getKeyName());
        Object userId = request.getAttribute(AuthKeyEnum.USER_ID.getKeyName());
        if (userId != null) {
            account = userId.toString();
        }
        if (appId != null) {
            account = appId.toString();
        }
        AuthToken authToken = apiAuthencator.reAuth(account, baseUrl);
        Cookie tokenCookie = new Cookie(AuthKeyEnum.TOKEN.getKeyName(), authToken.getToken());
        Cookie createTimeCookie = new Cookie(AuthKeyEnum.CREATE_TIME.getKeyName(),
                String.valueOf(authToken.getCreateTime()));
        response.addCookie(tokenCookie);
        response.addCookie(createTimeCookie);
        return ApiResponse.success(null);
    }
}
