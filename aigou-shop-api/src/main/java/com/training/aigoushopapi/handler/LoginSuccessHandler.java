package com.training.aigoushopapi.handler;
import com.alibaba.fastjson2.JSON;
import com.training.aigoushopapi.entity.UserVO;
import com.training.aigoushopapi.util.JwtUtils;
import com.training.aigoushopapi.util.RsaUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @author 十五
 * 登录成功的处理器
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获得登录的用户名
        UserDetails userDetails = (UserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails == null) {
            throw new RuntimeException("user is not login");
        }
        //生成JWT字符串
        String token = JwtUtils.generateToken(userDetails.getUsername(), RsaUtils.privateKey, JwtUtils.EXPIRE_MINUTES);
        //发送给前端
        UserVO userVO = new UserVO();
        userVO.setToken(token);
        ResponseEntity<UserVO> entity = ResponseEntity.ok(userVO);
        response.getWriter().write(JSON.toJSONString(entity));
    }
}
