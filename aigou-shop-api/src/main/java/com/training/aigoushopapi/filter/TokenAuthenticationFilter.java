package com.training.aigoushopapi.filter;

import com.training.aigoushopapi.util.JwtUtils;
import com.training.aigoushopapi.util.RsaUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author 十五
 * JWT token验证过滤器
 */
@Component
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 拦截请求，判断请求头是否包含token
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 配置白名单路径
        List<String> whiteList = List.of("/user/login", "/user/add", "/product/getProductSalesList", "/product/allCategoryProduct", "/carouselFigure/all", "/productType/all", "/product/newProduct");
        // 跳过无需验证的路径
        String path = request.getServletPath();
        if (whiteList.contains(path)) {
            chain.doFilter(request, response);
            return;
        }
        // 验证 Token
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new RuntimeException("token is null");
        }
        try {
            String userName = JwtUtils.getUsernameFromToken(token, RsaUtils.publicKey);
            System.out.println("userName --> " + userName);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userName, "", AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // 放行请求
        chain.doFilter(request, response);
    }

}
