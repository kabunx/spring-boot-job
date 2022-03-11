package com.kabunx.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一的日志结构体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebLogEntity implements Serializable {
    private static final long serialVersionUID = -2347002893218319774L;
    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求类型
     */
    private String method;

    /**
     * URL
     */
    private String uri;

    /**
     * 请求参数
     */
    private Object args;

    /**
     * 请求类和方法
     */
    private String signature;
}
