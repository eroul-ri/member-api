package com.eroul.api.common;


import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private String accessToken;

    @Builder
    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
