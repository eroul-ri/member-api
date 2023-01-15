package com.eroul.api.member.controller;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.common.TokenDto;
import com.eroul.api.member.dto.MemberSignInReq;
import com.eroul.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1")
@RestController
public class AuthController {
    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원 로그인처리
     * @param memberSignInReq
     * @return
     */
    @Operation(summary = "회원 로그인", description = "휴대폰번호 또는 아이디를 입력받아 로그인 처리합니다.")
    @PostMapping("/authentication")
    public ResponseEntity<CommonRespDto<TokenDto>> signInMember(@RequestBody @Valid MemberSignInReq memberSignInReq) {

        TokenDto tokenDto = memberService.signInProcess(memberSignInReq);

        return ResponseEntity.ok(CommonRespDto.successWithData(tokenDto));
    }
}
