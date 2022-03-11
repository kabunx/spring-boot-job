package com.kabunx.core.web.resource;

import com.kabunx.core.exception.JsonException;
import com.kabunx.core.util.JsonUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 集合数据转化
 */
public class CollectionResource {
    public static <S, T> List<T> toView(List<S> records, Class<T> clazz) {
        return records.stream()
                .map(record -> {
                    try {
                        return JsonUtils.toBean(record, clazz);
                    } catch (JsonException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}
