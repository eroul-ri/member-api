package com.eroul.api;

import com.eroul.api.member.domain.Member;
import com.eroul.api.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void existsMemberEmail() {
        String email = "askyws@nate.com";

        boolean exist = memberRepository.existsByEmail(email);

        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);


        Assert.assertTrue(memberOptional.isPresent() == exist);
    }
}
