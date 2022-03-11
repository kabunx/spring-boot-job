package com.kabunx.core.web;

import com.kabunx.core.constant.enums.ResponseEnum;
import com.kabunx.core.constant.enums.ExceptionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public JsonResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), data);
    }

    public static <T> JsonResponse<T> nullSuccess() {
        return success(null);
    }

    public static <T> JsonResponse<T> error(Integer code, String message, T data) {
        return new JsonResponse<>(code, message, data);
    }

    public static <T> JsonResponse<T> error(Integer code, String message) {
        return error(code, message, null);
    }

    public static <T> JsonResponse<T> error(ExceptionEnum exceptionEnum, T data) {
        return error(exceptionEnum.getCode(), exceptionEnum.getMessage(), data);
    }

    public static <T> JsonResponse<T> error(ExceptionEnum exceptionEnum) {
        return error(exceptionEnum, null);
    }

    public static <T> JsonResponse<T> error(T data) {
        return error(ExceptionEnum.UNDEFINED, data);
    }

    public static <T> JsonResponse<T> error() {
        return error(ExceptionEnum.UNDEFINED);
    }
}
