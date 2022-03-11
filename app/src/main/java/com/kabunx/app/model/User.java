package com.kabunx.app.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登陆账号
     */
    private String account;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 性别【0保密，1男，2女】
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱认证时间
     */
    private Date emailVerifiedAt;

    /**
     * 密码
     */
    private String password;

    /**
     * 记住我
     */
    private String rememberToken;

    /**
     *
     */
    private Date createdAt;

    /**
     *
     */
    private Date updatedAt;

    /**
     * 软删时间
     */
    private Date deletedAt;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private List<Permission> permissions;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}