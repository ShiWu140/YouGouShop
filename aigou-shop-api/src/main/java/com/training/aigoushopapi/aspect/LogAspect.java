package com.training.aigoushopapi.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * AOP日志切面
 * 用于记录请求日志，包括请求参数、响应结果、执行时间等信息
 * @author 十五
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private final ObjectMapper objectMapper;

    public LogAspect() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 定义切点，拦截所有controller包下的方法
     */
    @Pointcut("execution(* com.training.aigoushopapi.controller..*.*(..))")
    public void logPointCut() {
    }

    /**
     * 环绕通知，记录方法执行的时间、参数、返回值等信息
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 记录请求信息
        String requestURI = request != null ? request.getRequestURI() : "";
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();

        // 处理请求参数，过滤掉文件类型参数
        String params = Arrays.stream(point.getArgs())
                .map(arg -> {
                    if (arg instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) arg;
                        return "MultipartFile[" + file.getOriginalFilename() + "]";
                    }
                    try {
                        return objectMapper.writeValueAsString(arg);
                    } catch (Exception e) {
                        return String.valueOf(arg);
                    }
                })
                .collect(Collectors.joining(", "));

        String logMessage = "请求开始 - URI: " + requestURI + ", 方法: " + methodName + ", 参数: " + params;
        logger.info(logMessage);

        // 执行方法
        Object result = null;
        try {
            result = point.proceed();
            long endTime = System.currentTimeMillis();

            // 处理响应结果，避免日志过大
            String resultStr;
            try {
                if (result != null) {
                    resultStr = objectMapper.writeValueAsString(result);
                    // 如果结果太长，截断显示
                    if (resultStr.length() > 1000) {
                        resultStr = resultStr.substring(0, 1000) + "...(已截断)";
                    }
                } else {
                    resultStr = "null";
                }
            } catch (Exception e) {
                resultStr = String.valueOf(result);
            }

            logMessage = "请求结束 - URI: " + requestURI + ", 方法: " + methodName + ", 耗时: " + (endTime - beginTime) + "ms, 返回值: " + resultStr;
            logger.info(logMessage);
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logMessage = "请求异常 - URI: " + requestURI + ", 方法: " + methodName + ", 耗时: " + (endTime - beginTime) + "ms, 异常信息: " + e.getMessage();
            logger.error(logMessage, e);
            throw e;
        }
    }
}
