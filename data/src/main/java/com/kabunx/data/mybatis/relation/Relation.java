package com.kabunx.data.mybatis.relation;

import com.kabunx.core.support.ListStream;
import com.kabunx.data.mybatis.mapper.PlusMapper;
import com.kabunx.data.mybatis.wrapper.PlusWrapper;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
public abstract class Relation<R, T> {

    /**
     * 自定义主键
     */
    protected String localKey = "id";

    /**
     * 自定义外键
     */
    protected String relatedKey;

    /**
     * 主键数据集合收集器，用于关联查询
     */
    protected Function<T, ?> localCollector;

    /**
     * 关联数据分组器，用于将数据回填主数据
     */
    protected Function<R, ?> relatedGroupBy;

    /**
     * 关联Mapper
     */
    protected PlusMapper<R> relatedMapper;

    /**
     * 关联Wrapper
     */
    protected PlusWrapper<R> relatedWrapper;

    /**
     * 关联数据
     */
    protected Map<Object, List<R>> relatedItems = new HashMap<>();

    public void lazyLoad(List<T> localItems) {
        if (localItems != null && isValidConditions()) {
            initRelatedItems(localItems);
            handleAndMergeRelated2Local(localItems);
        }
        clear();
    }

    public abstract void handleAndMergeRelated2Local(List<T> localItems);

    public void initRelatedItems(List<T> localItems) {
        List<Object> collection = collectLocalKey(localItems);
        if (!collection.isEmpty()) {
            PlusWrapper<R> wrapper = getRelatedWrapper();
            wrapper.in(relatedKey, collection);
            List<R> records = relatedMapper.selectList(wrapper);
            relatedItems = groupRelatedItems(records);
        }
    }

    /**
     * 清理过程数据
     */
    public void clear() {
        relatedItems.clear();
        relatedWrapper = null;
    }

    public List<Object> collectLocalKey(List<T> localItems) {
        return new ListStream<>(localItems).pluck(r -> {
            if (localCollector != null) {
                return localCollector.apply(r);
            }
            return getDeclaredFieldValue(r, localKey);
        });
    }

    public Map<Object, List<R>> groupRelatedItems(List<R> items) {
        return new ListStream<>(items).groupBy(r -> {
            if (relatedGroupBy != null) {
                return relatedGroupBy.apply(r);
            }
            return getDeclaredFieldValue(r, relatedKey);
        });
    }

    public PlusWrapper<R> getRelatedWrapper() {
        if (relatedWrapper == null) {
            relatedWrapper = new PlusWrapper<>();
        }
        return relatedWrapper;
    }

    protected R getOneRelatedItem(T item, String ownerKey) {
        List<R> items = getManyRelatedItems(item, ownerKey);
        return (items == null || items.isEmpty()) ? null : items.get(0);
    }

    protected List<R> getManyRelatedItems(T item, String ownerKey) {
        Object key;
        if (localCollector != null) {
            key = localCollector.apply(item);
        } else {
            key = getDeclaredFieldValue(item, ownerKey);
        }
        return relatedItems.getOrDefault(key, null);
    }

    /**
     * 必须是合法的条件才能获取关联数据
     */
    protected boolean isValidConditions() {
        return relatedMapper != null && relatedKey != null;
    }

    private Object getDeclaredFieldValue(Object obj, String name) {
        if (name == null) {
            return null;
        }
        try {
            Field field = obj.getClass().getField(snakeToCamel(name));
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Ascii码依次比较法
     *
     * @param word 需转化的字符串
     * @return 转化的字符串
     */
    private String snakeToCamel(String word) {
        if (word == null) {
            return null;
        }
        String[] pieces = word.split("_");
        StringBuilder sb = new StringBuilder(word.length());
        for (String piece : pieces) {
            char[] chars = piece.toCharArray();
            if (chars[0] >= 'a' && chars[0] <= 'z') {
                chars[0] -= 32;
            }
            sb.append(chars);
        }
        return toFirstLower(sb.toString());
    }

    public String toFirstLower(String name) {
        char[] chars = name.toCharArray();
        if (Character.isLowerCase(chars[0])) {
            return name;
        }
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
