package com.eroul.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorFields {
    private String fieldName;
    private String errorMessage;
}
