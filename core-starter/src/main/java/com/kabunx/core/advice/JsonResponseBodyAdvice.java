package com.kabunx.core.advice;

import com.kabunx.core.exception.JsonException;
import com.kabunx.core.util.JsonUtils;
import com.kabunx.core.web.JsonResponse;
import com.kabunx.core.web.annotation.IgnoreJsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应拦截器
 */
@Slf4j
@RestControllerAdvice
@Order(1)
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasParameterAnnotation(IgnoreJsonResponse.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        response.setStatusCode(HttpStatus.OK);
        if (body == null) {
            return JsonResponse.nullSuccess();
        } else if (body instanceof String) {
            try {
                return toJsonString(JsonResponse.success(body));
            } catch (JsonException e) {
                log.error("String类型转化错误", e);
                return JsonResponse.error();
            }
        } else if (body instanceof JsonResponse) {
            return body;
        }
        return JsonResponse.success(body);
    }

    private String toJsonString(JsonResponse<Object> body) throws JsonException {
        return JsonUtils.stringify(body);
    }
}
