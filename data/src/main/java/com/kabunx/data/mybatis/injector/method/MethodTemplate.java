package com.kabunx.data.mybatis.injector.method;

/**
 * 自定义SQL模版
 */
public enum MethodTemplate {
    JOIN_PIVOT(
            "joinPivotWrapper",
            "关联中间键查询",
            "<script>SELECT %s FROM %s %s %s\n</script>"
    );
    private final String method;
    private final String description;
    private final String sql;

    MethodTemplate(String method, String description, String sql) {
        this.method = method;
        this.description = description;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public String getSql() {
        return sql;
    }
}
