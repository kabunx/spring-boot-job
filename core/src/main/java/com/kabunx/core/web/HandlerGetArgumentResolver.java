package com.kabunx.core.web;

import com.kabunx.core.util.JsonUtils;
import com.kabunx.core.web.annotation.GetParam;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * 自定义获取GET请求的所有参数
 */
public class HandlerGetArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        GetParam getParam = parameter.getParameterAnnotation(GetParam.class);
        return getParam != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        // 第一版简单转化
        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        Class<?> valueType = resolvableType.resolve();
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        Map<String, String> result = CollectionUtils.newLinkedHashMap(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                result.put(key, values[0]);
            }
        });
        return JsonUtils.toBean(result, valueType);
    }
}
