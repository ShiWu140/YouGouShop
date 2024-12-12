package com.training.aigouapi;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author ethan
 */
@SpringBootApplication
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        // 启动Spring Boot应用
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        // 获取Environment实例
        Environment env = context.getEnvironment();

        // 获取端口号和上下文路径
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path", "");

        // 输出启动信息
        logger.info("\n\n===========> 系统启动成功！后台地址：http://localhost:" + serverPort + contextPath);
    }
}
