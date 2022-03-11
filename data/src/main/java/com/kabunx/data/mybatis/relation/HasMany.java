package com.kabunx.data.mybatis.relation;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * 一对多关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HasMany<R, T> extends Relation<R, T> {

    /**
     * 将关联数据合并到主数据
     */
    protected BiConsumer<T, List<R>> merge;

    public void setRelatedArgs(String relatedKey, String localKey) {
        this.relatedKey = relatedKey;
        this.localKey = localKey;
    }

    @Override
    public void handleAndMergeRelated2Local(List<T> localItems) {
        if (merge == null) {
            return;
        }
        for (T item : localItems) {
            merge.accept(item, getManyRelatedItems(item, localKey));
        }
    }
}
