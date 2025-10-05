package com.cm.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义用户名或密码为恐异常
 */
public class UsernameOrPasswordNullException extends AuthenticationException {
    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }

    public UsernameOrPasswordNullException(String msg, Throwable t) {
        super(msg, t);
    }
}
