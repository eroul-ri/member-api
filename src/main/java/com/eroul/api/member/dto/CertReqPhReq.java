package com.eroul.api.member.dto;

import com.eroul.api.common.validator.PhNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CertReqPhReq {
    @NotBlank
    @Schema(description = "통신사", allowableValues = {"SKT", "LGT", "KT", "ETC"})
    private String telecomCode;

    @PhNumber
    @Schema(description = "휴대전화번호", maxLength = 11)
    private String phNumber;
}
