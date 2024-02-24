package com.xianyue.common.core.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xianyue.common.core.serial.BigDecimalSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: XianYueJacksonConfig
 * @Package: com.xianyue.common.core.config
 * @Description: Jackson序列化格式自定义
 * @Author: xianyue
 * @Date: 2024/2/24 11:21
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.xianyue.serial.on")
public class XianYueJacksonConfig {
    @Bean
    public Module bigDecimalModule() {
        SimpleModule bigDecimalModule = new SimpleModule();
        bigDecimalModule.addSerializer(BigDecimalSerializer.instance);
        return bigDecimalModule;
    }
}
