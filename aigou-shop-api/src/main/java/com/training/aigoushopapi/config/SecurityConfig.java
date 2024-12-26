package com.training.aigoushopapi.config;
import com.training.aigoushopapi.filter.TokenAuthenticationFilter;
import com.training.aigoushopapi.handler.LoginSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @author 十五
 */
@Configuration
public class SecurityConfig {
    @Resource
    private UserDetailsService userDetailsService;

    //提供密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置 AuthenticationManager 验证配置
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 全局认证配置
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    // 配置 SecurityFilterChain 授权配置
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenAuthenticationFilter) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/user/login", "/user/add").permitAll() // 配置白名单
                        .anyRequest().authenticated() // 其他请求需要认证
                )
                .formLogin(login -> login
                        .loginProcessingUrl("/user/login") // 登录处理 URL
                        .successHandler(new LoginSuccessHandler()) // 登录成功处理器
                        .failureHandler((req, resp, e) -> { // 登录失败处理器
                            resp.getWriter().write("{\"message\":\"login failed\"}");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // 登出 URL
                        .logoutSuccessHandler((req, resp, e) -> { // 登出成功处理器
                            resp.getWriter().write("{\"message\":\"logout success\"}");
                        })
                        .clearAuthentication(true)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((req, resp, e) -> { // 未认证处理
                            resp.setStatus(401);
                            resp.getWriter().write("{\"message\":\"not login\"}");
                        })
                )
                // 使用新的方式禁用 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        // 无状态 Session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 使用新的方式配置跨域
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 添加 Token 过滤器
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 配置允许的源
        configuration.setAllowedOrigins(List.of("http://localhost:8091"));
        // 配置允许的方法
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 配置允许的请求头
        configuration.setAllowedHeaders(List.of("*"));
        // 允许发送凭证
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
