package com.kabunx.core.web.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryRequest implements Serializable {
    private static final long serialVersionUID = 1770556762106355780L;

    /**
     * 当前分页
     */
    private Integer page = 1;

    /**
     * 每页获取的数据量
     */
    private Integer pageSize = 20;

    /**
     * 排序参数
     * &sorts=id:desc,age:asc
     */
    private String sorts;
}
