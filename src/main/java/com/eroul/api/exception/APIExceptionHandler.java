package com.eroul.api.exception;

import com.eroul.api.common.CommonRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoHandlerFoundException.class, ChangeSetPersister.NotFoundException.class})
    public CommonRespDto handleNotFound() {
        return CommonRespDto.notFound();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public CommonRespDto handleConstraintViolation(ConstraintViolationException exception) {
        return CommonRespDto.badRequest(getMessage(exception.getConstraintViolations().iterator()));
    }

    private String getMessage(Iterator<ConstraintViolation<?>> violationIterator) {
        StringBuilder builder = new StringBuilder();
        while (violationIterator.hasNext()) {
            ConstraintViolation<?> constraintViolation = violationIterator.next();

            builder.append(constraintViolation.getMessage());

            if(violationIterator.hasNext()) {
                builder.append("::");
            }
        }

        return builder.toString();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonRespDto handleException(Exception exception) {
        log.error(" INTERNAL SERVER ERROR : {}", exception.getMessage());
        return CommonRespDto.error("INTERNAL SERVER ERROR");
    }
}
