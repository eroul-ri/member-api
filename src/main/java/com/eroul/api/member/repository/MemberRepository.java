package com.eroul.api.member.repository;

import com.eroul.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /** 이메일 중복검사 */
    boolean existsByEmail(String email);
    /** 휴대폰 번호 중복 검사*/
    boolean existsByPhNumber(String phNumber);
    /** 회원정보 조회 단건 BY Email */
    Optional<Member> findMemberByEmail(String email);
    /** 회원정보 조회 단건 BY PhNumber */
    Optional<Member> findMemberByPhNumber(String phNumber);
    /** 회원정보 조회 단건 BY Email OR PhNumber */
    Optional<Member> findMemberByEmailOrPhNumber(String email, String phNumber);
}
