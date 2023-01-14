package com.eroul.api.member.dto;

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
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
           , message = "올바른 형식의 이메일을 입력해주세요.")
    @NotBlank
    @Schema(description = "이메일")
    private String email;

    @Pattern(regexp = "^[가-힣]{2,20}$", message = "이름은 한글 2 ~ 20자 이내로 입력해주세요.")
    @NotBlank
    private String name;

    @Length(max = 20)
    private String nickName;

    @Pattern(regexp = "^010(?:\\d{3}|\\d{4})\\d{4}$", message = "올바른 형식의 휴대전화번호를 입력해주세요.")
    @NotBlank
    private String phNumber;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,30}$"
           , message = "패스워드는 영문, 숫자, 특수문자 포함 6 ~ 30자리 이내로 입력해주세요.")
    @NotBlank
    private String password;

    public Member toEntity() {
        String nickName = this.nickName;
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
