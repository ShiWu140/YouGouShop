package com.training.aigoushopapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/19 09:13
 * 新增 WebConfig 类实现 WebMvcConfigurer 接口
 * 配置跨域请求（CORS）设置，允许所有来源的请求
 * 支持 GET、POST、PUT、DELETE 和 OPTIONS 方法
 * 允许所有头部信息和凭证- 设置预检请求的缓存时间为 3600 秒
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}