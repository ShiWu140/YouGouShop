package com.training.aigoushopapi;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;


/**
 * @author shiwu
 * @since 2024-12-18
 */
@EnableCaching
@SpringBootApplication
@MapperScan("com.training.aigoushopapi.mapper")
public class AigouShopApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(AigouShopApiApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AigouShopApiApplication.class, args);
        // 获取Environment实例
        Environment env = context.getEnvironment();

        // 获取端口号和上下文路径
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path", "");

        // 输出启动信息
        String message = "\n\n===========> 系统启动成功！后台地址：http://localhost:" + serverPort + contextPath;
        logger.info(message);
    }

}
