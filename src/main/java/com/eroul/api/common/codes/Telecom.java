package com.eroul.api.common.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Telecom {
    SKT("SKT")
    , LGT("LGT")
    , KT("KT")
    , ETC("ETC");

    private String code;

    public static Telecom findByCode(String code) {
        for(Telecom telecom : Telecom.values()) {
            if(telecom.getCode().equals(code)) {
                return telecom;
            }
        }

        return null;
    }

    public static String [] telecomCodes() {
        return Arrays.stream(Telecom.values())
                .map(v-> v.getCode())
                .toArray(String[]::new);
    }
}
