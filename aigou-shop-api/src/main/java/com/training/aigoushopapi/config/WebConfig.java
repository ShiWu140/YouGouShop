package com.training.aigoushopapi.config;

import com.training.aigoushopapi.exception.UserTokenException;
import com.training.aigoushopapi.interceptor.UserTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/19 09:13
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }

    /**
     * 注册Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserTokenInterceptor()).addPathPatterns("/**");
    }
    
}