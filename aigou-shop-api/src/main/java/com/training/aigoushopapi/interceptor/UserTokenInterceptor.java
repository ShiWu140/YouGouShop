package com.training.aigoushopapi.interceptor;

import com.training.aigoushopapi.exception.UserTokenException;
import com.training.aigoushopapi.util.JwtUtils;
import com.training.aigoushopapi.util.RsaUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 验证token拦截器
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/19 09:38
 */
public class UserTokenInterceptor implements HandlerInterceptor {
    //放行的白名单
    private static final String[] whiteList = {"login", "register", "upload"};

    //请求访问后台接口的前置处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //对所有白名单放行
        for (String url : whiteList) {
            if (request.getRequestURI().contains(url)) {
                return true;
            }
        }
        // 解决跨域
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        //该字段可选，是个布尔值，表示是否可以携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");

        //如果是 OPTIONS 请求，让 OPTIONS 请求返回一个200状态码（解决获取不到token）
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }
        //获得请求头中token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return true;
//            throw new UserTokenException("token为空");
        }
        //验证token
        try {
            String username = JwtUtils.getUsernameFromToken(token, RsaUtils.publicKey);
            System.out.println("解析成功 " + username);
            //true 放行 false 拦截
            return true;
        } catch (Exception ex) {
            return true;
            //throw new UserTokenException("token解析失败");
        }
    }
}
