package com.eroul.api.member.dto;


import com.eroul.api.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResp {
    private Long id;

    private String email;
    private String phNumber;
    private String name;
    private String nickName;

    public MemberResp(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.phNumber = member.getPhNumber();
        this.name = member.getName();
        this.nickName = member.getNickName();
    }
}
