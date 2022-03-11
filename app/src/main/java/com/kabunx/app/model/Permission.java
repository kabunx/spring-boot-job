package com.kabunx.app.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName rbac_permission
 */
@TableName(value = "rbac_permission")
@Data
public class Permission implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     *
     */
    private Date createdAt;

    /**
     *
     */
    private Date updatedAt;

    /**
     * 多对多关联，主要用于关联数据的分组
     */
    private Long pivotLocalId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}