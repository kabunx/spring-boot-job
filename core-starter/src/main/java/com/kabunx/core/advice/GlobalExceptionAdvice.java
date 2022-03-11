package com.kabunx.core.advice;

import com.kabunx.core.constant.enums.ExceptionEnum;
import com.kabunx.core.exception.BizException;
import com.kabunx.core.web.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestControllerAdvice
@Order(0)
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = BizException.class)
    public JsonResponse<Object> handle(BizException e) {
        log.error(e.getMessage());
        if (e.getExceptionEnum() != null) {
            return JsonResponse.error(e.getExceptionEnum());
        }
        return JsonResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public JsonResponse<Object> handleValidException(HttpMessageConversionException e) {
        log.error(e.getMessage());
        return JsonResponse.error(ExceptionEnum.CONVERSION);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResponse<Object> handleValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return JsonResponse.error(ExceptionEnum.VALIDATE_FAILED, getBindingErrors(e.getBindingResult()));
    }

    @ExceptionHandler(value = BindException.class)
    public JsonResponse<Object> handleValidException(BindException e) {
        log.error(e.getMessage());
        return JsonResponse.error(ExceptionEnum.VALIDATE_FAILED, getBindingErrors(e.getBindingResult()));
    }

    /**
     * 错误信息收集
     */
    private HashMap<String, ArrayList<String>> getBindingErrors(BindingResult bindingResult) {
        HashMap<String, ArrayList<String>> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                ArrayList<String> fErrors = errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>());
                fErrors.add(fieldError.getDefaultMessage());
            }
        }
        return errors;
    }
}
