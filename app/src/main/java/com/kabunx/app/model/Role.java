package com.kabunx.app.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName rbac_role
 */
@TableName(value = "rbac_role")
@Data
public class Role implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 多对多关联，主要用于关联数据的分组
     */
    @TableField(exist = false)
    private Long pivotLocalId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}