package com.kabunx.core.web.controller;

import com.kabunx.core.exception.JsonException;
import com.kabunx.core.pagination.LengthPagination;
import com.kabunx.core.pagination.SimplePagination;
import com.kabunx.core.version.APIVersion;
import com.kabunx.core.web.JsonResponse;
import com.kabunx.core.web.resource.CollectionResource;
import com.kabunx.core.web.resource.ObjectResource;
import com.kabunx.core.web.resource.PaginationResource;

import java.util.List;

/**
 * 将被转为标准的响应
 */
public class ApiController {
    /**
     * 分页响应
     */
    protected <S, T> JsonResponse<LengthPagination<T>> toPagination(LengthPagination<S> pagination, Class<T> clazz) {
        return JsonResponse.success(
                PaginationResource.toView(pagination, clazz)
        );
    }

    /**
     * 简单分页响应
     */
    protected <S, T> JsonResponse<SimplePagination<T>> toPagination(SimplePagination<S> pagination, Class<T> clazz) {
        return JsonResponse.success(
                PaginationResource.toView(pagination, clazz)
        );
    }

    /**
     * 集合处理
     */
    protected <S, T> JsonResponse<List<T>> toCollection(List<S> records, Class<T> clazz) {
        return JsonResponse.success(
                CollectionResource.toView(records, clazz)
        );
    }

    /**
     * 单条数据处理
     */
    protected <T> JsonResponse<T> toItem(Object source, Class<T> clazz) throws JsonException {
        return JsonResponse.success(
                ObjectResource.toView(source, clazz)
        );
    }

    /**
     * @param obj 输出对象
     * @return 响应
     */
    protected JsonResponse<Object> toItem(Object obj) {
        return JsonResponse.success(obj);
    }

    /**
     * 没有任何输出
     */
    protected JsonResponse<Object> toNull() {
        return JsonResponse.nullSuccess();
    }
}
