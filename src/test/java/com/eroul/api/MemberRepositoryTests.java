package com.eroul.api;

import com.eroul.api.member.domain.Member;
import com.eroul.api.member.dto.MemberResp;
import com.eroul.api.member.dto.MemberSignUpReq;
import com.eroul.api.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member memberToEntity(MemberSignUpReq memberSignUpReq) {
        return memberSignUpReq.toEntity();
    }

    @Test
    public void existsMemberEmail() {
        String email = "askyws@nate.com";

        boolean exist = memberRepository.existsByEmail(email);

        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);


        Assert.assertTrue(memberOptional.isPresent() == exist);
    }


    @Test
    public void signupProcessTest() {
        MemberSignUpReq memberSignUpReq = new MemberSignUpReq(
                "askyws@nate.com",
                "변혜리",
                "",
                "01038811393",
                "asasa112344"
        );
        // 1. 이메일 중복검사
        Assert.assertFalse("이미 존재하는 이메일입니다.", memberRepository.existsByEmail(memberSignUpReq.getEmail()));
        // 2. 휴대전화번호 중복검사
        Assert.assertFalse("이미 존재하는 휴대전화번호입니다.", memberRepository.existsByPhNumber(memberSignUpReq.getPhNumber()));
        // 3. 패스워드 인코딩
        Member member = memberToEntity(memberSignUpReq);

        member.setEncodePassword(passwordEncoder.encode(memberSignUpReq.getPassword()));

        Assert.assertTrue(passwordEncoder.matches(memberSignUpReq.getPassword(), member.getPassword()));

        registerMember(member);
        log.info("memberInfo ::{}", member);

        log.info("transfer memberResp", new MemberResp(member));
    }

    @Transactional
    public void registerMember(Member member) {
        memberRepository.save(member);
    }
}
