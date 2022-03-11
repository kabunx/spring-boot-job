package com.kabunx.core.web.resource;

import com.kabunx.core.exception.JsonException;
import com.kabunx.core.pagination.LengthPagination;
import com.kabunx.core.pagination.SimplePagination;
import com.kabunx.core.util.JsonUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页数据转化
 */
public class PaginationResource {

    public static <S, T> LengthPagination<T> toView(LengthPagination<S> pagination, Class<T> clazz) {
        List<T> records = map(pagination.getList(), clazz);
        return new LengthPagination<>(pagination.getCurrent(), pagination.getTotal(), records);
    }

    public static <S, T> SimplePagination<T> toView(SimplePagination<S> pagination, Class<T> clazz) {
        List<T> records = map(pagination.getList(), clazz);
        return new SimplePagination<>(pagination.getHasMore(), records);
    }

    private static <S, T> List<T> map(List<S> records, Class<T> clazz) {
        return records.stream()
                .map(obj -> {
                    try {
                        return JsonUtils.toBean(obj, clazz);
                    } catch (JsonException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}
