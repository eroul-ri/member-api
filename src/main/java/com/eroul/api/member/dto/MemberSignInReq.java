package com.eroul.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberSignInReq {
    @NotBlank
    @Schema(description = "이메일주소 또는 휴대전화번호")
    private String username;

    @NotBlank
    @Schema(description = "패스워드")
    private String password;
}
