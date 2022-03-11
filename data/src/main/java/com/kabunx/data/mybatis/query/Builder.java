package com.kabunx.data.mybatis.query;

import com.kabunx.core.constant.enums.ExceptionEnum;
import com.kabunx.core.exception.BizException;
import com.kabunx.core.pagination.LengthPagination;
import com.kabunx.core.pagination.SimplePagination;
import com.kabunx.data.mybatis.mapper.PlusMapper;
import com.kabunx.data.mybatis.relation.*;
import com.kabunx.data.mybatis.wrapper.PlusWrapper;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 自定义高级查询构造器
 */
public class Builder<T, Children extends Builder<T, Children>> {
    /**
     * 占位符
     */
    protected final Children children = (Children) this;

    private PlusMapper<T> mapper;

    private PlusWrapper<T> wrapper;

    private String[] columns;

    private final HashMap<String, String> orders = new HashMap<>();

    private Integer offsetNum;

    private Integer limitNum;

    private final HashMap<String, Relation<?, T>> relations = new HashMap<>();

    private final HashMap<String, Consumer<PlusWrapper<?>>> loadRelations = new HashMap<>();

    /**
     * 依赖注入完成后执行
     */
    @PostConstruct
    private void loadAfterConstruct() {
        defineAllRelations();
    }

    /**
     * 预定义关联关系
     */
    public void defineAllRelations() {

    }

    /**
     * 用于处理注解依赖
     */
    public PlusMapper<T> getMapper() {
        return mapper;
    }

    public PlusWrapper<T> getWrapper() {
        if (wrapper == null) {
            wrapper = new PlusWrapper<>();
        }
        return wrapper;
    }

    public Children select(String... columns) {
        this.columns = columns;
        return children;
    }

    public Children filter(Consumer<PlusWrapper<T>> filter) {
        filter.accept(getWrapper());
        return children;
    }

    public Children orderByAsc(String column) {
        orders.put(column, "asc");
        return children;
    }

    public Children orderByDesc(String column) {
        orders.put(column, "desc");
        return children;
    }

    public Children offset(Integer num) {
        offsetNum = num;
        return children;
    }

    /**
     * alias for offset
     */
    public Children skip(Integer num) {
        return offset(num);
    }

    public Children limit(Integer num) {
        limitNum = num;
        return children;
    }

    public Children forPage(Integer page, Integer pageSize) {
        return offset((page - 1) * pageSize).limit(pageSize);
    }

    public Children forPageById(Integer page, Integer pageSize) {
        orders.clear();
        return orderByDesc("id").forPage(page, pageSize);
    }

    public Children with(String relation, Consumer<PlusWrapper<?>> filter) {
        if (!loadRelations.containsKey(relation)) {
            loadRelations.put(relation, filter);
        }
        return children;
    }

    public Children with(String relation) {
        return with(relation, null);
    }

    public Long count(Consumer<PlusWrapper<T>> filter) {
        PlusWrapper<T> wrapper = getWrapper();
        if (filter != null) {
            filter.accept(wrapper);
        }
        return getMapper().selectCount(wrapper);
    }

    public Long count() {
        return count(null);
    }

    public List<T> get(Boolean chunk) {
        PlusWrapper<T> wrapper = getWrapper();
        if (columns != null && columns.length > 0) {
            wrapper.select(columns);
        }
        if (!orders.isEmpty()) {
            orders.forEach((key, value) -> {
                if ("desc".equalsIgnoreCase(value)) {
                    wrapper.orderByDesc(key);
                } else {
                    wrapper.orderByAsc(key);
                }
            });
        }
        if (limitNum != null) {
            if (offsetNum != null) {
                wrapper.last("LIMIT " + offsetNum + "," + limitNum);
            } else {
                wrapper.last("LIMIT " + limitNum);
            }
        }
        List<T> records = getMapper().selectList(wrapper);
        lazyLoadRelationsData(records);
        if (chunk) {
            clearByChunk();
        } else {
            clear();
        }

        return records;
    }

    public List<T> get() {
        return get(false);
    }

    public boolean chunk(Integer count, Function<List<T>, Boolean> callback, String column) {
        List<T> records;
        int page = 1;
        do {
            if (column != null) {
                orders.clear();
                orderByDesc(column);
            }
            records = forPage(page, count).get(true);
            if (records == null || records.isEmpty()) {
                break;
            }
            boolean result = callback.apply(records);
            if (!result) {
                return false;
            }
            page++;
        } while (records.size() == count);
        clear();
        return true;
    }

    public boolean chunk(Integer count, Function<List<T>, Boolean> callback) {
        return chunk(count, callback, null);
    }

    public boolean chunkById(Integer count, Function<List<T>, Boolean> callback) {
        return chunk(count, callback, "id");
    }

    public T find(Serializable id) {
        return mapper.selectById(id);
    }

    public T findOrFail(Serializable id) throws BizException {
        T record = find(id);
        if (record == null) {
            throw new BizException(ExceptionEnum.NOT_FOUND);
        }
        return record;
    }

    public T first() {
        List<T> records = limit(1).get();
        return records == null || records.isEmpty() ? null : records.get(0);
    }

    public T firstOrFail() throws BizException {
        T record = first();
        if (record == null) {
            throw new BizException(ExceptionEnum.NOT_FOUND);
        }
        return record;
    }

    public T latest(String column) {
        return orderByDesc(column).first();
    }

    public T latestOrFail(String column) throws BizException {
        T record = latest(column);
        if (record == null) {
            throw new BizException(ExceptionEnum.NOT_FOUND);
        }
        return record;
    }

    public T sole() throws BizException {
        List<T> records = limit(2).get();
        if (records == null || records.isEmpty()) {
            throw new BizException(ExceptionEnum.NOT_FOUND);
        }
        if (records.size() != 1) {
            throw new BizException(ExceptionEnum.NOT_ONLY);
        }
        return records.get(0);
    }

    public LengthPagination<T> paginate(Integer page, Integer pageSize) {
        // 1、获取总数
        Long count = count();
        // 2、查询结果集
        List<T> records = new ArrayList<>();
        if (count > 0) {
            records = forPage(page, pageSize).get();
        }
        return new LengthPagination<>(page, count, records);
    }

    public SimplePagination<T> simplePaginate(Integer page, Integer pageSize) {
        // 1、向下多取1条
        pageSize += 1;
        List<T> records = forPage(page, pageSize).get();
        // 2、分页
        return new SimplePagination<>(
                records.size() > pageSize,
                records.subList(0, pageSize)
        );
    }

    /**
     * 定义一对一关系
     *
     * @param name     关系名称
     * @param callback 关系回调，用于设置参数
     * @param <R>      关系模型
     */
    public <R> void hasOne(String name, Consumer<HasOne<R, T>> callback) {
        HasOne<R, T> oneRelation = new HasOne<>();
        addRelations(name, oneRelation);
        callback.accept(oneRelation);
    }

    /**
     * 定义一对多关系
     *
     * @param name     关系名称
     * @param callback 关系回调，用于设置参数
     * @param <R>      关系模型
     */
    public <R> void hasMany(String name, Consumer<HasMany<R, T>> callback) {
        HasMany<R, T> manyRelation = new HasMany<>();
        addRelations(name, manyRelation);
        callback.accept(manyRelation);
    }

    public <R> void belongsTo(String name, Consumer<BelongsTo<R, T>> callback) {
        BelongsTo<R, T> belongsToRelation = new BelongsTo<>();
        addRelations(name, belongsToRelation);
        callback.accept(belongsToRelation);
    }

    public <R> void belongsToMany(String name, Consumer<BelongsToMany<R, T>> callback) {
        BelongsToMany<R, T> belongsToManyRelation = new BelongsToMany<>();
        addRelations(name, belongsToManyRelation);
        callback.accept(belongsToManyRelation);
    }

    public void clearByChunk() {
        orders.clear();
        offsetNum = null;
        limitNum = null;
    }

    public void clear() {
        columns = null;
        orders.clear();
        offsetNum = null;
        limitNum = null;
        wrapper.clear();
        loadRelations.clear();
    }

    protected <R> void addRelations(String name, Relation<R, T> relation) {
        if (!relations.containsKey(name)) {
            relations.put(name, relation);
        }
    }

    private void lazyLoadRelationsData(List<T> records) {
        loadRelations.forEach((key, filter) -> {
            if (relations.containsKey(key)) {
                Relation<?, T> relation = relations.get(key);
                if (filter != null) {
                    filter.accept(relation.getRelatedWrapper());
                }
                relation.lazyLoad(records);
            }
        });
    }
}
