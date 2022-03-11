package com.kabunx.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kabunx.data.property.MybatisProperties;
import org.apache.ibatis.reflection.MetaObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FillMetaObjectHandler implements MetaObjectHandler {

    @Resource
    MybatisProperties mybatisProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        String column = mybatisProperties.getCreatedColumn();
        switch (mybatisProperties.getTimestamp()) {
            case "seconds":
                this.strictInsertFill(metaObject, column, this::getSeconds, Integer.class);
                break;
            case "millis":
                this.strictInsertFill(metaObject, column, this::getMillis, Long.class);
                break;
            case "datetime":
                this.strictInsertFill(metaObject, column, this::getDate, Date.class);
                break;
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String column = mybatisProperties.getUpdatedColumn();
        switch (mybatisProperties.getTimestamp()) {
            case "seconds":
                this.strictUpdateFill(metaObject, column, this::getSeconds, Integer.class);
                break;
            case "millis":
                this.strictUpdateFill(metaObject, column, this::getMillis, Long.class);
                break;
            case "datetime":
                this.strictUpdateFill(metaObject, column, this::getDate, Date.class);
                break;
        }
    }

    private Integer getSeconds() {
        return (int) TimeUnit.MICROSECONDS.toSeconds(System.currentTimeMillis());
    }

    private Long getMillis() {
        return System.currentTimeMillis();
    }

    private Date getDate() {
        return new Date();
    }
}
