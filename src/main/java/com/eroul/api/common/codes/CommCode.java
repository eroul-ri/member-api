package com.eroul.api.common.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommCode {
    SUCCESS("0000", "Request Success")
    , FAIL("0001", "Request Fail")
    , ERROR("-9998", "Error")
    , NOTFOUND("-4444", "Not Found")
    , BADREQ("-9999", "Bad Request");

    private String code;
    private String message;
}
