package com.training.aigoushopapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class AigouShopApiApplicationTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void testPassword() {
        for (int i = 0; i < 5; i++) {
            System.out.println(
                    passwordEncoder.encode("123456"));
        }
    }
}
