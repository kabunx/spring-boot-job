package com.kabunx.app.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_password_reset
 */
@TableName(value ="user_password_reset")
@Data
public class UserPasswordReset implements Serializable {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 验证码
     */
    private String token;

    /**
     * 生成时间
     */
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}