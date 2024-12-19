package com.training.aigoushopapi.annotation;

import java.lang.annotation.*;

/**
 * 响应结果注解
 * 在类或方法上加入此注解后返回结果将自动被包装
 *
 * @Author: Hor
 * @Date: 2024/12/11 15:50
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME) // 运行时
@Target({ElementType.METHOD, ElementType.TYPE}) // 类或方法上
@Documented
public @interface ResponseResult { }