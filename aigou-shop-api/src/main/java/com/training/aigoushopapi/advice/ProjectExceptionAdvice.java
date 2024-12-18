package com.training.aigoushopapi.advice;

import com.training.aigoushopapi.common.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/13 02:49
 */
@RestControllerAdvice
public class ProjectExceptionAdvice {
    private static final Logger logger = Logger.getLogger(ProjectExceptionAdvice.class);

    @ExceptionHandler
    public Result handleException(Exception e) {
        logger.error(e.getMessage());
        return Result.error("服务器开小差了，请稍后重试！");
    }
}
