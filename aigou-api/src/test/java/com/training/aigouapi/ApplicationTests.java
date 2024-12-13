package com.training.aigouapi;

import com.training.aigouapi.mapper.BrandMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ApplicationTests {
@Resource
    BrandMapper brandMapper;
    @Test
    void contextLoads() {
        brandMapper.selectAll().forEach(System.out::println);
    }
}
