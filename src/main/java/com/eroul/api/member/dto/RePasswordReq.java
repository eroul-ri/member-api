package com.eroul.api.member.dto;

import com.eroul.api.common.validator.Password;
import com.eroul.api.common.validator.PhNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RePasswordReq {
    @Password
    @Schema(description = "패스워드", maxLength = 30)
    private String rePassword;

    @PhNumber
    @Schema(description = "휴대전화번호", maxLength = 11)
    private String phNumber;
}
