package com.eroul.api.exception;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.common.codes.CommCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 401 handling
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OutputStream out = response.getOutputStream();

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(out, CommonRespDto.commonExceptionHandle(CommCode.UNAUTHORIZED, StringUtils.EMPTY));

        out.flush();
    }
}
