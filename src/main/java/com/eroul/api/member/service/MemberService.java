package com.eroul.api.member.service;

import com.eroul.api.common.TokenDto;
import com.eroul.api.common.utils.TokenUtil;
import com.eroul.api.member.domain.Member;
import com.eroul.api.member.dto.*;
import com.eroul.api.member.repository.MemberRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthDetailService authDetailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenUtil tokenUtil;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthDetailService authDetailService, AuthenticationManagerBuilder authenticationManagerBuilder, TokenUtil tokenUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authDetailService = authDetailService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenUtil = tokenUtil;
    }

    public boolean isExistMemberEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isExistMemberPhNumber(String phNumber) {
        return memberRepository.existsByPhNumber(phNumber);
    }

    public String genOrderNumber() {
        return  "SMS" + RandomStringUtils.randomNumeric(4) + System.currentTimeMillis();
    }

    public String sendCertification(CertReqPhReq certReqPhReq) {
        // ?????? ???????????? ??????
        return genOrderNumber();
    }

    public boolean verify(VeriPhReq veriPhReq) {
        // ?????? ???????????? ??????.
        return true;
    }

    public MemberResp signupProcess(MemberSignUpReq memberSignUpReq) {
        // ????????? ????????????
        if(isExistMemberEmail(memberSignUpReq.getEmail())) {
            return null;
        }
        // ?????????????????? ????????????
        if(isExistMemberPhNumber(memberSignUpReq.getPhNumber())) {
            return null;
        }
        Member member = memberSignUpReq.toEntity();
        // ???????????? ????????? ??????
        member.setEncodePassword(passwordEncoder.encode(memberSignUpReq.getPassword()));
        // ????????????
        memberRepository.save(member);

        return new MemberResp(member);
    }

    @Transactional
    public MemberResp setRePassword(RePasswordReq rePasswordReq) {
        Optional<Member> memberOptional = memberRepository.findMemberByPhNumber(rePasswordReq.getPhNumber());

        if(!memberOptional.isPresent()) {
            throw new NotFoundException("???????????? ?????? ??????????????? ?????????.");
        }
        Member member = memberOptional.get();

        member.setEncodePassword(passwordEncoder.encode(rePasswordReq.getRePassword()));

        return new MemberResp(member);
    }

    public TokenDto signInProcess(MemberSignInReq memberSignInReq) {
        UserDetails userDetails = authDetailService.loadUserByUsername(memberSignInReq.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), memberSignInReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //????????????
        return tokenUtil.generateToken(authentication);
    }

    public MemberResp getMemberInfoByEmail(String email) {
        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

        if(!memberOptional.isPresent()) {
            throw new NotFoundException("???????????? ?????? ??????????????? ?????????.");
        }

        return new MemberResp(memberOptional.get());
    }
}
