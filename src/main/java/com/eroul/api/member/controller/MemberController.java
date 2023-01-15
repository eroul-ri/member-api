package com.eroul.api.member.controller;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.common.ErrorFields;
import com.eroul.api.common.codes.Telecom;
import com.eroul.api.common.validator.MemberEmail;
import com.eroul.api.exception.ExistUserException;
import com.eroul.api.member.dto.CertReqPhReq;
import com.eroul.api.member.dto.MemberResp;
import com.eroul.api.member.dto.MemberSignUpReq;
import com.eroul.api.member.dto.RePasswordReq;
import com.eroul.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RequestMapping("/v1")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 이메일 중복검사
     * @param email
     * @return
     */
    @Operation(summary = "이메일 중복검사", description = "이메일 주소를 중복검사합니다.")
    @GetMapping("/member/email/{email:.*}")
    public ResponseEntity<CommonRespDto<Boolean>> checkEmailExists(@PathVariable @MemberEmail String email) {
        boolean exist = memberService.isExistMemberEmail(email);

        if(exist) {
            throw new ExistUserException("이미 존재하는 회원입니다.");
        }
        return ResponseEntity.ok(
                CommonRespDto.successWithData(false)
        );
    }

    /**
     * 휴대폰 번호 인증발송처리
     * @param certReqPhReq
     * @return
     */
    @Operation(summary = "휴대폰 번호 인증 발송", description = "임의로 발송됐다는 가정하에 발송 KEY 값을 리턴합니다.")
    @PostMapping("/member/phone")
    public ResponseEntity<CommonRespDto<?>> sendCertificationMemberPhone(@RequestBody @Valid CertReqPhReq certReqPhReq) {
        // 통신사 정보 미일치
        if(Telecom.findByCode(certReqPhReq.getTelecomCode()) == null) {
            List<ErrorFields> errorFields = new ArrayList<>();

            errorFields.add(new ErrorFields("telecomCode", "통신사 코드는 지정된 값만 입력해주세요."));

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonRespDto.badRequest(errorFields));
        }

        return ResponseEntity.ok(CommonRespDto.successWithData(memberService.sendCertification(certReqPhReq)));
    }

    /**
     * 회원가입 처리
     * @param memberSignUpReq
     * @return
     */
    @Operation(summary = "회원가입", description = "회원 정보를 입력받아 회원가입 처리합니다.", responses = {
        @ApiResponse(responseCode = "201", description = "signup success"),
        @ApiResponse(responseCode = "409", description = "member duplicated")
    })
    @PutMapping("/member/signup")
    public ResponseEntity<CommonRespDto<MemberResp>> signUpMember(@RequestBody @Valid MemberSignUpReq memberSignUpReq) {
        MemberResp memberResp = memberService.signupProcess(memberSignUpReq);

        if(memberResp == null) {
            throw new ExistUserException("이미 존재하는 회원입니다.");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonRespDto.successWithData(memberResp));
    }

    /**
     * 패스워드 재설정
     * @param rePasswordReq
     * @return
     */
    @Operation(summary = "패스워드 재설정", description = "휴대폰 번호와 패스워드를 입력받아 패스워드를 재설정합니다.")
    @PatchMapping("/member/password")
    public ResponseEntity<CommonRespDto<MemberResp>> passwordReset(@RequestBody @Valid RePasswordReq rePasswordReq) {
        MemberResp memberResp = memberService.setRePassword(rePasswordReq);

        return ResponseEntity.ok(CommonRespDto.successWithData(memberResp));
    }


}
