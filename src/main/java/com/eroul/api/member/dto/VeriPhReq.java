package com.eroul.api.member.dto;

import com.eroul.api.common.validator.PhNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class VeriPhReq {
    @PhNumber
    @Schema(description = "휴대전화번호", maxLength = 11)
    private String phNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4,}$", message = "인증번호는 숫자 4자리로 입력해주세요.")
    @Schema(description = "인증 번호", maxLength = 4)
    private String veriNumber;
}
