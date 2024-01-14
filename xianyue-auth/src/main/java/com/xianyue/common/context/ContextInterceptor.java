package com.xianyue.common.context;

import cn.hutool.core.util.NumberUtil;
import com.xianyue.common.context.vo.XianYueContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Title: XianYueContextInterceptor
 * @Package: com.xianyue.common.context.interceptor
 * @Description: 应用程序上下文获取拦截器
 * @Author: xianyue
 * @Date: 2024/1/14 19:55
 */
@Slf4j
@Component
public class ContextInterceptor implements HandlerInterceptor {
    @Value("${xianyue.excludeService:}")
    private String[] excludeServices;

    /**
     * 上下文管理类
     */
    @Autowired
    private ContextManagerImpl xianYueContextManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        XianYueContext xianYueContext = new XianYueContext();
        xianYueContext.setToken(request.getHeader("token"));
        xianYueContext.setAppId(request.getHeader("appId"));
        String userId = request.getHeader("userId");
        String createTime = request.getHeader("createTime");
        if (NumberUtil.isLong(userId)) {
            xianYueContext.setUserId(Long.parseLong(userId));
        }
        if (NumberUtil.isLong(createTime)) {
            xianYueContext.setCreateTime(Long.parseLong(createTime));
        }
        xianYueContextManager.setCurrentContext(xianYueContext);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        xianYueContextManager.removeCurrent();
    }
}
