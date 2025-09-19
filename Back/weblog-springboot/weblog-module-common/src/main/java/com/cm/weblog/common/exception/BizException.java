package com.cm.weblog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常类
 */
@Getter
@Setter
public class BizException extends RuntimeException {
    private String errorCode;

    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
