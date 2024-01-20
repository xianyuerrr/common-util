package com.xianyue.common.context.manager;

import com.xianyue.common.context.vo.XianYueContext;

/**
 * @Title: XianYueContextManager
 * @Package: com.xianyue.common.context
 * @Description: 应用程序上下文管理
 * @Author: xianyue
 * @Date: 2024/1/14 20:05
 */
public interface ContextManager {
    /**
     * @return 获取当前应用程序 XianYue 上下文
     */
    XianYueContext getCurrentContext();
}
