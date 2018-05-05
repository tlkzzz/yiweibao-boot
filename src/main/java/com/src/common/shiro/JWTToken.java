package com.src.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;


/**
 * 实现AuthenticationToken
 */
public class JWTToken implements AuthenticationToken {
    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
