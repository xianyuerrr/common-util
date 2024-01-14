package com.xianyue.common.context.config;

import com.xianyue.common.context.ContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Title: WebMvcConfigurer
 * @Package: com.xianyue.common.context
 * @Description:
 * @Author: xianyue
 * @Date: 2024/1/14 21:31
 */
@Configuration
public class XianYueContextConfigurer implements WebMvcConfigurer {
    @Autowired
    private ContextInterceptor contextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示对所有请求都拦截
        // .excludePathPatterns("/base/index") 表示排除对/base/index请求的拦截
        // 多个拦截器可以设置order顺序，值越小，preHandle越先执行，postHandle和afterCompletion越后执行
        // order默认的值是0，如果只添加一个拦截器，可以不显示设置order的值
        registry.addInterceptor(contextInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/base/index").order(0);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    }
}
