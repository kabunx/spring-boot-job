package com.kabunx.core.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SimplePagination<T> {
    private Boolean hasMore;
    private List<T> list;
}
