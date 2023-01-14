package com.eroul.api.member.service;

import com.eroul.api.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean isExistMemberEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isExistMemberPhNumber(String phNumber) {
        return memberRepository.existsByPhNumber(phNumber);
    }


}
