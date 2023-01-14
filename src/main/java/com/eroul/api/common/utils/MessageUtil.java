package com.eroul.api.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageUtil {

    private MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * argument 없는 메시지
     * @param code 메시지 코드
     * @return
     */
    public String getMessage(String code) {
        try {
            return getMessage(code, new String[0]);
        } catch(NoSuchMessageException me) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * argument 있는 메시지
     * @param code
     * @param args
     * @return
     */
    public String getMessage(String code, List<String> args) {
        String[] ar = args.toArray(new String[args.size()]);
        try {
            return messageSource.getMessage(code, ar, LocaleContextHolder.getLocale());
        } catch(NoSuchMessageException me) {
            return StringUtils.EMPTY;
        }
    }

    public String getMessage(String code, String[] args) {
        try {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch(NoSuchMessageException me) {
            return StringUtils.EMPTY;
        }
    }
}