package com.training.aigoushopapi.interceptor;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 验证token拦截器
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/19 09:38
 */
public class UserTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        // 判断是否为空，为空则抛出异常
        if (token == null) {
            throw new RuntimeException("用户未登录");
        }
        // 校验token是否正确，这里直接返回true，模拟校验通过
        return true;
    }
}
