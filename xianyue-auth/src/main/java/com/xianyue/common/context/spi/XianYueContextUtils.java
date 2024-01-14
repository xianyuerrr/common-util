package com.xianyue.common.context.spi;

import com.xianyue.common.context.ContextManager;
import com.xianyue.common.context.vo.XianYueContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Title: XianYueContextUtils
 * @Package: com.xianyue.common.context
 * @Description: 应用程序上下文工具类
 * @Author: xianyue
 * @Date: 2024/1/14 21:44
 */
@Component
public class XianYueContextUtils implements ApplicationContextAware {
    private static ContextManager contextManager;

    public static XianYueContext getContext() {
        return Objects.requireNonNull(contextManager.getCurrentContext());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        XianYueContextUtils.contextManager = Objects.requireNonNull(applicationContext.getBean(ContextManager.class));
    }
}
