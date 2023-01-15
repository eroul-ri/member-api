package com.eroul.api;

import com.eroul.api.member.domain.Member;
import com.eroul.api.member.dto.MemberResp;
import com.eroul.api.member.dto.MemberSignUpReq;
import com.eroul.api.member.dto.RePasswordReq;
import com.eroul.api.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
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

    @Before
    @Test
    public void beforeDo() {
        MemberSignUpReq memberSignUpReq = new MemberSignUpReq(
                "askyws@aa.com",
                "벼네리",
                "",
                "01045454544",
                "1q2w3e4r!"
        );
        Member member = memberToEntity(memberSignUpReq);

        member.setEncodePassword(passwordEncoder.encode(memberSignUpReq.getPassword()));
        registerMember(member);
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

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    @Test
    @Transactional
    public void rePasswordTest() {
        signupProcessTest();

        RePasswordReq rePasswordReq = new RePasswordReq("01044444444", "01038811393");

        Optional<Member> memberOptional = memberRepository.findMemberByPhNumber(rePasswordReq.getPhNumber());

        Assert.assertTrue(memberOptional.isPresent());

        Member member = memberOptional.get();

        member.setEncodePassword(passwordEncoder.encode(rePasswordReq.getRePassword()));

        getMember(rePasswordReq.getPhNumber(), member.getPassword());
    }

    public void getMember(String key, String testPassword) {
        Optional<Member> memberOptional = memberRepository.findMemberByPhNumber(key);

        Assert.assertTrue(memberOptional.isPresent());

        Member member = memberOptional.get();

        Assert.assertTrue(member.getPassword().equals(testPassword));
    }

    @Test
    public void getMemberByEmail() {
        String email = "askyws@aa.com";
        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

        Assert.assertTrue(memberOptional.isPresent());

    }
}
