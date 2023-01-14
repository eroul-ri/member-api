package com.eroul.api.member.service;

import com.eroul.api.member.domain.Member;
import com.eroul.api.member.dto.MemberResp;
import com.eroul.api.member.dto.MemberSignUpReq;
import com.eroul.api.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isExistMemberEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isExistMemberPhNumber(String phNumber) {
        return memberRepository.existsByPhNumber(phNumber);
    }

    public MemberResp signupProcess(MemberSignUpReq memberSignUpReq) {
        // 이메일 중복검사
        if(isExistMemberEmail(memberSignUpReq.getEmail())) {
            return null;
        }
        // 휴대전화번호 중복검사
        if(isExistMemberPhNumber(memberSignUpReq.getPhNumber())) {
            return null;
        }
        Member member = memberSignUpReq.toEntity();
        // 패스워드 암호화 처리
        member.setEncodePassword(passwordEncoder.encode(memberSignUpReq.getPassword()));
        // 저장처리
        memberRepository.save(member);

        return new MemberResp(member);
    }
}
