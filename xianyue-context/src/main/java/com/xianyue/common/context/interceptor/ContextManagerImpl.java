package com.xianyue.common.context.interceptor;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.xianyue.common.context.manager.ContextManager;
import com.xianyue.common.context.vo.XianYueContext;
import org.springframework.stereotype.Component;

/**
 * @Title: XianYueContextManagerImpl
 * @Package: com.xianyue.common.context
 * @Description: 应用程序上下文管理类
 * @Author: xianyue
 * @Date: 2024/1/14 20:07
 */
@Component
public class ContextManagerImpl implements ContextManager {
    /**
     * 应用程序上下文，使用 ThreadLocal 存储，TransmittableThreadLocal 适配线程池，以解决普通 ThreadLocal 只能在线程创建时传递的问题·
     * <a href="https://juejin.cn/post/7010976461326647310">...</a>
     */
    private static final TransmittableThreadLocal<XianYueContext> TTL = new TransmittableThreadLocal<>();

    @Override
    public XianYueContext getCurrentContext() {
        return TTL.get();
    }

    /**
     * @param xianYueContext 设置当前应用程序上下文，此 method 不对本组件外开放
     */
    protected void setCurrentContext(XianYueContext xianYueContext) {
        TTL.set(xianYueContext);
    }

    /**
     * 移除当前上下文
     */
    protected void removeCurrent() {
        TTL.remove();
    }
}
