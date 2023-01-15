package com.eroul.api.member.dto;

import com.eroul.api.common.validator.MemberEmail;
import com.eroul.api.common.validator.Password;
import com.eroul.api.common.validator.PhNumber;
import com.eroul.api.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class MemberSignUpReq {
    @Length(max = 150)
    @MemberEmail
    @Schema(description = "이메일", maxLength = 150, required = true)
    private String email;

    @Pattern(regexp = "^[가-힣]{2,20}$", message = "이름은 한글 2 ~ 20자 이내로 입력해주세요.")
    @NotBlank
    @Schema(description = "이름", maxLength = 20, required = true)
    private String name;

    @Length(max = 20)
    @Schema(description = "닉네임", maxLength = 20)
    private String nickName;

    @PhNumber
    @Schema(description = "휴대전화번호", maxLength = 11, required = true)
    private String phNumber;

    @Password
    @Schema(description = "패스워드", maxLength = 30, required = true)
    private String password;

    public Member toEntity() {
        String nickName = this.nickName;
        // 닉네임 미입력 : 랜덤 문자열 생성
        if(StringUtils.isEmpty(this.nickName)) {
            nickName = RandomStringUtils.randomAlphabetic(6);
        }
        return Member.builder()
                     .email(this.email)
                     .name(this.name)
                     .nickName(nickName)
                     .password(this.password)
                     .phNumber(this.phNumber)
                     .build();
    }
}
