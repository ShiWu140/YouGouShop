package com.training.aigouapi.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/11 14:25
 */
@Aspect
@Component
public class SystemLogAspect {
    //定义日志对象
    private final Logger logger = Logger.getLogger(SystemLogAspect.class);

    //定义切面
    @Pointcut("execution(* com.training.aigouapi.service.impl.*ServiceImpl.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        logger.debug("执行方法：" + name);
        //输出方法参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.debug("参数：" + arg);
        }
        try {
            long start = System.currentTimeMillis();

            Object rs = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.debug("返回值：" + rs);
            logger.debug("方法执行耗时(毫秒)：" + (end - start));
            return rs;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}