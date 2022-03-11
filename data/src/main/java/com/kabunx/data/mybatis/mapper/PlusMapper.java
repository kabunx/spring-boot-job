package com.kabunx.data.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlusMapper<T> extends BaseMapper<T> {
    List<T> joinPivotWrapper(
            @Param("pivot_table") String pivotTable,
            @Param("pivot_columns") String pivotColumns,
            @Param("pivot_local_key") String pivotLocalKey,
            @Param("pivot_related_key") String pivotRelatedKey,
            @Param(Constants.WRAPPER) QueryWrapper<T> queryWrapper
    );
}
