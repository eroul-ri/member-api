package com.eroul.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class ApiApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        Assert.assertNotNull(passwordEncoder);
    }

    /**
     * Bcrypt Encode 테스트
     */
    @Test
    public void passwordEncodingTest() {
        String password = "#1234";

        String encPassword = passwordEncoder.encode(password);

        log.info(" enc password : {}, {}", encPassword, encPassword.length());

        Assert.assertTrue(passwordEncoder.matches(password, encPassword));
    }
}
