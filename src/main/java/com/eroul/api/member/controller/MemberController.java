package com.eroul.api.member.controller;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

@Validated
@RequestMapping("/v1/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 1. 이메일 중복검사
     * @param email
     * @return
     */
    @Operation(description = "이메일 중복검사")
    @GetMapping("/email/{email:.*}")
    public ResponseEntity<CommonRespDto<Boolean>> checkEmailExists(@PathVariable @Email String email) {

        return ResponseEntity.ok(
                CommonRespDto.successWithData(memberService.isExistMemberEmail(email))
        );
    }

    /**
     * 휴대폰 번호 인증
     * PROCESS 1. 휴대폰 번호 형식확인
     * PROCESS 2. 휴대폰 번호 중복검사
     * PROCESS 3. 휴대폰 번호 인증번호 발송 처리
     */

    /**
     * 휴대폰 번호 인증완료 처리
     * PROCESS 1. 숫자 형식 4자리 일치여부 확인
     * -> 일치응답 처리
     */

    /**
     * 회원가입 처리
     * PROCESS 1. 회원가입 데이터 검증
     * PROCESS 2. 이메일 중복검사
     * PROCESS 3. 휴대폰 번호 중복검사
     */

    /**
     * 로그인
     */

    
}
