package com.eroul.api.member.service;

import com.eroul.api.member.domain.Member;
import com.eroul.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findMemberByEmailOrPhNumber(username, username);

        if(!memberOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("NO USER %s", username));
        }
        Member member = memberOptional.get();

        return new User(member.getEmail(), member.getPassword(), defaultAuthority());
    }

    private static Collection<? extends GrantedAuthority> defaultAuthority() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }
}
