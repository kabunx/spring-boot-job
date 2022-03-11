package com.kabunx.data.mybatis.relation;

import com.kabunx.data.mybatis.wrapper.PlusWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 多对多关系
 */
public class BelongsToMany<R, T> extends Relation<R, T> {

    /**
     * 连接表， 类比为"user_role"
     */
    protected String pivotTable;

    /**
     * 有时候需要链接表的字段信息
     */
    protected String[] pivotColumns;

    /**
     * 关联的模型在连接表里的外键名，类比为"user_role(user_id)"
     */
    protected String pivotLocalKey;

    /**
     * 另一个模型在连接表里的外键名，类比为"user_role(role_id)"
     */
    protected String pivotRelatedKey;

    /**
     * 自定义回调，关联数据回填到主属性中
     */
    private BiConsumer<T, List<R>> merge;

    public void setRelatedArgs(
            String pivotTable,
            String pivotLocalKey,
            String pivotRelatedKey
    ) {
        this.pivotTable = pivotTable;
        this.pivotLocalKey = pivotLocalKey;
        this.pivotRelatedKey = pivotRelatedKey;
    }

    public void setPivotColumns(String... pivotColumns) {
        this.pivotColumns = pivotColumns;
    }

    public void setMerge(BiConsumer<T, List<R>> merge) {
        this.merge = merge;
    }

    @Override
    public void initRelatedItems(List<T> items) {
        List<Object> collection = collectLocalKey(items);
        if (!collection.isEmpty()) {
            // 关联表中关联主键的条件
            PlusWrapper<R> wrapper = getRelatedWrapper();
            wrapper.in(String.format("%s.%s", pivotTable, pivotLocalKey), collection);
            List<String> columns = new ArrayList<>();
            if (this.pivotColumns != null) {
                for (String column : this.pivotColumns) {
                    if (!column.contains(pivotTable)) {
                        column = pivotTable + "." + column;
                    }
                    columns.add(column);
                }
            } else {
                columns.add(pivotTable + "." + pivotLocalKey);
                columns.add(pivotTable + "." + pivotRelatedKey);
            }
            List<R> records = relatedMapper.joinPivotWrapper(
                    pivotTable,
                    String.join(",", columns),
                    pivotLocalKey,
                    pivotRelatedKey,
                    wrapper
            );
            relatedItems = groupRelatedItems(records);
        }
    }

    @Override
    public void handleAndMergeRelated2Local(List<T> localItems) {
        for (T item : localItems) {
            merge.accept(item, getManyRelatedItems(item, localKey));
        }
    }

    @Override
    protected boolean isValidConditions() {
        return relatedMapper != null;
    }
}
