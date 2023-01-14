package com.eroul.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommCode {
    SUCCESS("0000", "Request Success")
    , FAIL("0001", "Request Fail");

    private String code;
    private String message;
}
