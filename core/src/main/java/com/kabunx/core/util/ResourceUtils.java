package com.kabunx.core.util;

import com.kabunx.core.exception.JsonException;
import com.kabunx.core.pagination.LengthPagination;
import com.kabunx.core.pagination.SimplePagination;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源数据映射工具类
 */
@Slf4j
public class ResourceUtils {

    public static <S, T> LengthPagination<T> mapLengthPagination(LengthPagination<S> pagination, Class<T> tClass) {
        List<T> records = mapCollection(pagination.getList(), tClass);
        return new LengthPagination<>(pagination.getCurrent(), pagination.getTotal(), records);
    }

    public static <S, T> SimplePagination<T> mapSimplePagination(SimplePagination<S> pagination, Class<T> tClass) {
        List<T> records = mapCollection(pagination.getList(), tClass);
        return new SimplePagination<>(pagination.getHasMore(), records);
    }

    public static <S, T> List<T> mapCollection(List<S> records, Class<T> tClass) {
        return records.stream()
                .map(record -> {
                    try {
                        return mapObject(record, tClass);
                    } catch (JsonException e) {
                        log.error(e.getMessage());
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public static <T> T mapObject(Object obj, Class<T> tClass) throws JsonException {
        return JsonUtils.toObject(obj, tClass);
    }
}
