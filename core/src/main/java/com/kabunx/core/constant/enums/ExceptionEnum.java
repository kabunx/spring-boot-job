package com.kabunx.core.constant.enums;

/**
 * 统一的异常枚举
 * 建议使用6位统一错误码
 * 前三位 404 配置错误码 http status
 * 后三位 001 功能领域
 */
public enum ExceptionEnum {
    UNDEFINED(100000, "未定义的异常"),
    FAILED(100001, "操作失败"),
    CONVERSION(400000, "超文本传输格式错误"),
    NOT_FOUND(404000, "资源不存在"),
    NOT_ONLY(400001, "数据不唯一"),
    VALIDATE_FAILED(422000, "参数检验失败"),
    UNAUTHORIZED(401000, "暂未登录或token已经过期"),
    FORBIDDEN(403000, "没有相关权限"),
    DB_FAILED(500002, "数据操作失败");

    private final Integer code;
    private final String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
