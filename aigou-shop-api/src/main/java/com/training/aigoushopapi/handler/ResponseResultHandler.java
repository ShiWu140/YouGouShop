package com.training.aigoushopapi.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.common.Result;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局统一响应返回体处理
 *
 * @Author: Hor
 * @Date: 2024/12/11 16:13
 * @Version: 1.0
 */
@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    /**
     * supports方法: 判断是否要执行beforeBodyWrite方法,
     * true为执行,false不执行.
     * 通过该方法可以选择哪些类或那些方法的response要进行处理, 其他的不进行处理.
     *
     * @param returnType    MethodParameter
     * @param converterType Class
     * @return boolean
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseResult.class) || returnType.hasMethodAnnotation(ResponseResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 如果已经包装就无需在包装了
        if (body instanceof Result) {
            return body;
        } else if (body instanceof String) {
            // String 需要额外转换
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return Result.success(body);
        }
    }

}
