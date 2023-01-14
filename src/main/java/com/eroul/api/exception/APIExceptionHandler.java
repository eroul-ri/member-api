package com.eroul.api.exception;

import com.eroul.api.common.CommonRespDto;
import com.eroul.api.common.ErrorFields;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public CommonRespDto requestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        return CommonRespDto.error(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoHandlerFoundException.class, NotFoundException.class})
    public CommonRespDto handleNotFound() {
        return CommonRespDto.notFound();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonRespDto<List<ErrorFields>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return CommonRespDto.badRequest(getErrorFields(exception.getBindingResult()));
    }

    private List<ErrorFields> getErrorFields(BindingResult bindingResult) {
        return  bindingResult.getFieldErrors()
                .stream()
                .map(v-> new ErrorFields(v.getField(), v.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public CommonRespDto handleConstraintViolation(ConstraintViolationException exception) {
        return CommonRespDto.badRequest(getErrorFields(exception.getConstraintViolations()));
    }

    private List<ErrorFields> getErrorFields(Set<ConstraintViolation<?>> constraintViolationSet) {
        return constraintViolationSet
                .stream()
                .map(v-> new ErrorFields(((PathImpl) v.getPropertyPath()).getLeafNode().toString(), v.getMessage()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonRespDto handleException(Exception exception) {
        log.error(" INTERNAL SERVER ERROR : {}", exception.getMessage());
        return CommonRespDto.error("INTERNAL SERVER ERROR");
    }
}
