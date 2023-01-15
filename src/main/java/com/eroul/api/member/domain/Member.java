package com.eroul.api.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 사용자 이메일 */
    @Column(nullable = false, length = 150, unique = true)
    private String email;
    /** 사용자 전화번호 */
    @Column(nullable = false, length = 11, unique = true)
    private String phNumber;
    /** 사용자 이름 */
    @Column(nullable = false, length = 20)
    private String name;
    /** 사용자 패스워드 */
    @Column(nullable = false, length = 60)
    private String password;
    /** 사용자 닉네임 */
    @Column(nullable = false, length = 20)
    private String nickName;

    @Builder
    public Member(String email, String phNumber, String name, String password, String nickName) {
        this.email = email;
        this.phNumber = phNumber;
        this.name = name;
        this.password = password;
        this.nickName = nickName;
    }

    public void setEncodePassword(String encodePassword) {
        this.password = encodePassword;
    }
}
