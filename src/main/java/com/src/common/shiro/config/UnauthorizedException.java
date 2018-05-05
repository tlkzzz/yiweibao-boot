package com.src.common.shiro.config;


/**
 * 自定义异常
 */
public class UnauthorizedException  extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
