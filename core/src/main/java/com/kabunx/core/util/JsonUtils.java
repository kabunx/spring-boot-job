package com.kabunx.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kabunx.core.exception.JsonException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * json序列化与反序列工具类
 */
@Slf4j
public class JsonUtils {
    /**
     * ObjectMapper提供了读取和写入JSON的功能
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 单例模式，是线程安全的
     */
    public static ObjectMapper getObjectMapper() {
        // 不存在的属性，不转化，否则报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static TypeFactory getTypeFactory() {
        return getObjectMapper().getTypeFactory();
    }

    /**
     * 将对象序列化为JSON
     */
    public static String stringify(Object object) throws JsonException {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("序列化异常", e);
            throw new JsonException("序列化异常");
        }
    }

    /**
     * 将JSON字符串反序列化为对象
     */
    public static <T> T toObject(String json, Class<T> tClass) throws JsonException {
        try {
            return getObjectMapper().readValue(json, tClass);
        } catch (Exception e) {
            log.error("反序列化异常", e);
            throw new JsonException("反序列化异常");
        }
    }

    /**
     * 将JSON字符串转换为复杂类型的对象
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) throws JsonException {
        try {
            return getObjectMapper().readValue(json, typeReference);
        } catch (Exception e) {
            log.error("TypeReference反序列化异常", e);
            throw new JsonException("反序列化异常");
        }
    }

    /**
     * 将对象序列化后再反序列化为目标对象
     */
    public static <T> T toObject(Object object, Class<T> tClass) throws JsonException {
        return toObject(stringify(object), tClass);
    }

    /**
     * 将JSON字符串转换为list对象
     */
    public static <T> List<T> toList(String json, Class<T> tClass) throws JsonException {
        try {
            JavaType javaType = getTypeFactory().constructParametricType(List.class, tClass);
            return getObjectMapper().readValue(json, javaType);
        } catch (Exception e) {
            log.error("List反序列化异常", e);
            throw new JsonException("反序列化异常");
        }
    }

    /**
     * 将JSON字符串转换为Map对象
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) throws JsonException {
        try {
            JavaType javaType = getTypeFactory().constructParametricType(Map.class, kClass, vClass);
            return getObjectMapper().readValue(json, javaType);
        } catch (Exception e) {
            log.error("Map反序列化异常", e);
            throw new JsonException("反序列化异常");
        }
    }
}
