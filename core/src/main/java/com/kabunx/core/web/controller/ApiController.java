package com.kabunx.core.web.controller;

import com.kabunx.core.exception.JsonException;
import com.kabunx.core.pagination.LengthPagination;
import com.kabunx.core.pagination.SimplePagination;
import com.kabunx.core.util.ResourceUtils;
import com.kabunx.core.web.JsonResponse;

import java.util.List;

/**
 * 将被转为标准的响应
 */
public class ApiController {
    /**
     * 分页响应
     */
    protected <S, T> JsonResponse<LengthPagination<T>> sendPaginationResponse(LengthPagination<S> pagination, Class<T> clazz) {
        LengthPagination<T> result = ResourceUtils.mapLengthPagination(pagination, clazz);
        return JsonResponse.success(result);
    }

    /**
     * 简单分页响应
     */
    protected <S, T> JsonResponse<SimplePagination<T>> sendPaginationResponse(SimplePagination<S> pagination, Class<T> clazz) {
        SimplePagination<T> result = ResourceUtils.mapSimplePagination(pagination, clazz);
        return JsonResponse.success(result);
    }

    /**
     * 集合处理
     */
    protected <S, T> JsonResponse<List<T>> sendListResponse(List<S> records, Class<T> clazz) {
        List<T> result = ResourceUtils.mapCollection(records, clazz);
        return JsonResponse.success(result);
    }

    /**
     * 单条数据处理
     */
    protected <T> JsonResponse<T> sendObjectResponse(Object source, Class<T> clazz) throws JsonException {
        T result = ResourceUtils.mapObject(source, clazz);
        return JsonResponse.success(result);
    }

    /**
     * @param obj 输出对象
     * @return 响应
     */
    protected JsonResponse<Object> sendObjectResponse(Object obj) {
        return JsonResponse.success(obj);
    }

    /**
     * 没有任何输出
     */
    protected JsonResponse<Object> sendNullResponse() {
        return JsonResponse.nullSuccess();
    }
}
