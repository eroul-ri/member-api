package com.eroul.api.common.validator;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhNumberValidator implements ConstraintValidator<PhNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(value)
                && value.matches("^010(?:\\d{3}|\\d{4})\\d{4}$");
    }
}
