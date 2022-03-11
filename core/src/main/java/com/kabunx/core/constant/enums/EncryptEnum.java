package com.kabunx.core.constant.enums;

public enum EncryptEnum {
    EMPTY(0, "empty"),
    SMS(1, "sms"),
    LOGIN_ACCOUNT(2, "login account");
    private final int code;
    private final String type;

    EncryptEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
