package com.ygz.aspen.common.base;

/**
 * 身份认证异常
 * @author ygz
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
