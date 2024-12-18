package com.training.aigoushopapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author shiwu
 * @since 2024-12-18
 */
@SpringBootApplication
@MapperScan("com.training.aigoushopapi.mapper")
public class AigouShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AigouShopApiApplication.class, args);
    }

}
