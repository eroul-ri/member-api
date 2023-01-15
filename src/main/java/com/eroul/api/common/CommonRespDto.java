package com.eroul.api.common;

import com.eroul.api.common.codes.CommCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class CommonRespDto<T> {
    private String code;
    private String message;

    private T data;

    public CommonRespDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonRespDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 성공 응답
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonRespDto<T> successWithData(T data) {
        return new CommonRespDto<>(CommCode.SUCCESS.getCode(), CommCode.SUCCESS.getMessage(), data);
    }

    /**
     * 실패 응답
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonRespDto<T> fail(String message) {
        if(StringUtils.isEmpty(message)) {
            message = CommCode.FAIL.getMessage();
        }
        return new CommonRespDto<>(CommCode.FAIL.getCode(), message);
    }

    /**
     * 에러 응답
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonRespDto<T> error(String message) {
        return new CommonRespDto<>(CommCode.ERROR.getCode(), message);
    }

    /**
     *  400 에러 응답
     * @param data
     * @return
     */
    public static <T> CommonRespDto<T> badRequest(T data) {
        return new CommonRespDto<>(CommCode.BADREQ.getCode(), CommCode.BADREQ.getMessage(), data);
    }

    /**
     *  exception 대응 응답
     * @return
     */
    public static CommonRespDto commonExceptionHandle(CommCode commCode, String message) {
        if(StringUtils.isEmpty(message)) {
            message = commCode.getMessage();
        }
        return new CommonRespDto<>(commCode.getCode(), message);
    }

}
