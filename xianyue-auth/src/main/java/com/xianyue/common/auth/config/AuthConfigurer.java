package com.xianyue.common.auth.config;

import com.xianyue.common.auth.interceptor.AuthInterceptor;
import com.xianyue.common.auth.interfaces.AuthController;
import com.xianyue.common.auth.service.ApiAuthencator;
import com.xianyue.common.auth.service.helper.CredentialStorage;
import com.xianyue.common.auth.service.impl.DefaultApiAuthencatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权服务配置类
 * 基础配置：
 * 1. 默认 Api 权限校验器配置
 * 2. 鉴权拦截器注册
 * 3. 通用鉴权服务接口
 * <p>
 * 灵活配置：
 * 1. 鉴权开关：spring.xianyue.auth
 * 2. 豁免接口配置：spring.xianyue.auth.excludes
 *
 * @Title: WebMvcConfigurer
 * @Package: com.xianyue.common.context
 * @Description:
 * @Author: xianyue
 * @Date: 2024/1/14 21:31
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.xianyue.auth", value = "on", havingValue = "true", matchIfMissing = true)
public class AuthConfigurer implements WebMvcConfigurer {
    private static AuthInterceptor authInterceptor;

    @Value("${spring.xianyue.auth.excludes:}")
    private String[] excludes;

    /**
     * 拦截除登录服务、自定义排除服务之外的所有请求
     * 注：鉴权相关的服务不可自定义
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示对所有请求都拦截
        // .excludePathPatterns("/base/index") 表示排除对/base/index请求的拦截
        // 多个拦截器可以设置order顺序，值越小，preHandle越先执行，postHandle和afterCompletion越后执行
        // order默认的值是0，如果只添加一个拦截器，可以不显示设置order的值
        List<String> excludeList = new ArrayList<>();
        for (String exclude : excludes) {
            if (exclude.matches("/auth.+")) {
                continue;
            }
            excludeList.add(exclude);
        }
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/auth/login").excludePathPatterns(excludeList).order(0);
    }

    /**
     * 根据Api权限校验器（可自定义），配置鉴权拦截器对象
     * 使用 @Primary 避免拦截器对象被 mock，从而导致跳过鉴权
     *
     * @param apiAuthencator Api权限校验器
     */
    @Autowired
    public void authInterceptor(ApiAuthencator apiAuthencator) {
        authInterceptor = new AuthInterceptor(apiAuthencator);
    }

    /**
     * 加载鉴权接口
     *
     * @param apiAuthencator Api权限校验器
     * @return 鉴权 Controller
     */
    @Bean
    public AuthController authController(ApiAuthencator apiAuthencator) {
        return new AuthController(apiAuthencator);
    }

    /**
     * 当不存在扩展的Api权限校验器时，提供默认的Api权限校验器
     *
     * @param credentialStorage 获取密钥服务（不提供默认实现，必须自定义实现）
     * @return 默认的Api权限校验器
     */
    @Bean
    @ConditionalOnMissingBean({ApiAuthencator.class})
    public ApiAuthencator defaultApiAuthencator(CredentialStorage credentialStorage) {
        return new DefaultApiAuthencatorImpl(credentialStorage);
    }
}
