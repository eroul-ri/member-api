package com.eroul.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberSignInReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
