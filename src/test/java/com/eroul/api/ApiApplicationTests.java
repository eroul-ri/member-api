package com.eroul.api;

import com.eroul.api.member.domain.Member;
import com.eroul.api.member.dto.MemberSignUpReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ApiApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        Assert.assertNotNull(passwordEncoder);
    }

    public Member memberToEntity(MemberSignUpReq memberSignUpReq) {
        return memberSignUpReq.toEntity();
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
    
    @Test
    public void signUpReqToEntityTest() {
        MemberSignUpReq memberSignUpReq = new MemberSignUpReq(
                "askyws@nate.com",
                "변혜리",
                "",
                "01038811393",
                "asasa112344"
        );
        Member member = memberToEntity(memberSignUpReq);
        Assert.assertTrue(StringUtils.equals(memberSignUpReq.getEmail(), member.getEmail()));
    }

}
