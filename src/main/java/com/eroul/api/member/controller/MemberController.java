package com.eroul.api.member.controller;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.member.dto.MemberResp;
import com.eroul.api.member.dto.MemberSignUpReq;
import com.eroul.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Validated
@RequestMapping("/v1")
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
    @Operation(summary = "이메일 중복검사")
    @GetMapping("/member/email/{email:.*}")
    public ResponseEntity<CommonRespDto<Boolean>> checkEmailExists(
            @PathVariable
            @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "올바른 형식의 이메일을 입력해주세요.")
            String email
    ) {
        boolean exist = memberService.isExistMemberEmail(email);

        if(exist) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(CommonRespDto.fail("이미 존재하는 회원입니다."));
        }
        return ResponseEntity.ok(
                CommonRespDto.successWithData(exist)
        );
    }

    /**
     * 휴대폰 번호 인증처리
     * @param phNumber
     * @return
     */
    @Operation(summary = "휴대폰 번호 인증")
    @PostMapping("/member/phone/{phNumber}")
    public ResponseEntity<CommonRespDto<Boolean>> certificationMemberPhone(
            @PathVariable
            @Pattern(regexp = "^010(?:\\d{3}|\\d{4})\\d{4}$", message = "올바른 형식의 휴대전화번호를 입력해주세요.")
            String phNumber
    ) {

        return ResponseEntity.ok(CommonRespDto.successNoData());
    }

    /**
     * 휴대폰 번호 인증완료 처리
     * PROCESS 1. 숫자 형식 4자리 일치여부 확인
     * -> 일치응답 처리
     */

    /**
     * 회원가입 처리
     * @param memberSignUpReq
     * @return
     */
    @Operation(summary = "회원가입", responses = {
        @ApiResponse(responseCode = "201", description = "signup success"),
        @ApiResponse(responseCode = "409", description = "member duplicated")
    })
    @PutMapping("/member")
    public ResponseEntity<CommonRespDto<MemberResp>> signupProcess(@RequestBody @Valid MemberSignUpReq memberSignUpReq) {
        MemberResp memberResp = memberService.signupProcess(memberSignUpReq);

        if(memberResp == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(CommonRespDto.fail("이미 존재하는 회원입니다."));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonRespDto.successWithData(memberResp));
    }
    
    /**
     * 로그인
     */
    
}
