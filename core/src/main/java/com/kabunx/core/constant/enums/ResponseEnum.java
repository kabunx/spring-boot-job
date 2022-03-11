package com.kabunx.core.constant.enums;

public enum ResponseEnum {
    SUCCESS(0, "请求成功"),
    ERROR(100000, "请求失败");

    private final Integer code;
    private final String message;

    ResponseEnum(Integer code, String message) {
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
