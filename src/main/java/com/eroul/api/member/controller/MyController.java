package com.eroul.api.member.controller;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.member.dto.MemberResp;
import com.eroul.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/v1")
@SecurityRequirement(name = "Authorization")
@RestController
public class MyController {
    private final MemberService memberService;

    public MyController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 내정보 가져오기
     * @param principal
     * @return
     */
    @Operation(summary = "내 정보 조회", description = "사용자의 정보를 가져옵니다.")

    @GetMapping("/my")
    public ResponseEntity<CommonRespDto<MemberResp>> readMemberInfo(Principal principal) {
        String email = principal.getName();

        MemberResp memberResp = memberService.getMemberInfoByEmail(email);
        return ResponseEntity.ok(CommonRespDto.successWithData(memberResp));
    }
}
