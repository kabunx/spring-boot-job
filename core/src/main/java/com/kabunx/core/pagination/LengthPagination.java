package com.kabunx.core.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LengthPagination<T> {
    private long current;
    private long total;
    private List<T> list;
}
