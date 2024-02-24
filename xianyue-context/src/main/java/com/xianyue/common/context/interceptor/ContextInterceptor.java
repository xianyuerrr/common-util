package com.xianyue.common.context.interceptor;

import com.xianyue.common.context.vo.XianYueContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Title: XianYueContextInterceptor
 * @Package: com.xianyue.common.context.interceptor
 * @Description: 应用程序上下文获取拦截器
 * @Author: xianyue
 * @Date: 2024/1/14 19:55
 */
@Slf4j
@AllArgsConstructor
public class ContextInterceptor implements HandlerInterceptor {
    /**
     * 上下文管理类
     */
    private ContextManagerImpl xianYueContextManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        XianYueContext xianYueContext = new XianYueContext();
        Object appId = request.getAttribute("AppId");
        Object userId = request.getAttribute("UserId");
        if (appId != null) {
            xianYueContext.setAppId(appId.toString());
        }
        if (userId != null) {
            xianYueContext.setUserId(userId.toString());
        }

        xianYueContextManager.setCurrentContext(xianYueContext);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        xianYueContextManager.removeCurrent();
    }
}
