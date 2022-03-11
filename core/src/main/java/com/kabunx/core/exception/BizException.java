package com.kabunx.core.exception;

import com.kabunx.core.constant.enums.ExceptionEnum;

/**
 * 统一的API异常类
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1807199840146721070L;
    private ExceptionEnum exceptionEnum;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
