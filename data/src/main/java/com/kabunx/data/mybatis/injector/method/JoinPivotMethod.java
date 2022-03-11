package com.kabunx.data.mybatis.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JoinPivotMethod extends AbstractMethod {
    public JoinPivotMethod() {
        super("joinPivotWrapper");
    }

    @Override
    public MappedStatement injectMappedStatement(
            Class<?> mapperClass,
            Class<?> modelClass,
            TableInfo tableInfo
    ) {
        MethodTemplate joinPivot = MethodTemplate.JOIN_PIVOT;
        String sql = String.format(
                joinPivot.getSql(),
                getSelectColumns(tableInfo), // columns
                tableInfo.getTableName(), // table
                sqlJoinInfo(tableInfo),  // join sql
                sqlWhereEntityWrapper(true, tableInfo)  // where
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, joinPivot.getMethod(), sqlSource, tableInfo);
    }

    private String sqlJoinInfo(TableInfo tableInfo) {
        return String.format(
                "INNER JOIN ${pivot_table} ON %s.%s = ${pivot_table}.${pivot_related_key}",
                tableInfo.getTableName(),
                tableInfo.getKeyProperty()
        );
    }

    private String getSelectColumns(TableInfo tableInfo) {
        String select = ASTERISK;
        if (tableInfo.getResultMap() == null || tableInfo.isAutoInitResultMap()) {
            select = tableInfo.getAllSqlSelect();
        }
        // 拼接上表名，避免关联查询的字段冲突
        String[] columns = select.split(",");
        List<String> tableColumns = new ArrayList<>();
        for (String column : columns) {
            if (!column.startsWith(tableInfo.getTableName())) {
                column = tableInfo.getTableName() + "." + column;
            }
            tableColumns.add(column);
        }
        return SqlScriptUtils.convertChoose(
                String.format("%s != null and %s != null", WRAPPER, Q_WRAPPER_SQL_SELECT),
                SqlScriptUtils.unSafeParam(Q_WRAPPER_SQL_SELECT),
                getAllSelectColumns(tableColumns)
        );
    }

    private String getAllSelectColumns(List<String> tableColumns) {
        // add pivot select columns
        tableColumns.add("${pivot_columns}");
        // 将用于关联数据的获取
        tableColumns.add("${pivot_table}.${pivot_local_key} AS pivot_local_id");
        return String.join(",", tableColumns);
    }

}
