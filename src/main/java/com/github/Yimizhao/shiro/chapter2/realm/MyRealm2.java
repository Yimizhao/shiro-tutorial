package com.github.Yimizhao.shiro.chapter2.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm2 implements Realm {
    @Override
    public String getName() {
        return MyRealm2.class.getName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = token.getPrincipal().toString();
        String password = String.valueOf((char[])token.getCredentials());

        if ("zhang".equals(username) && "123456".equals(password)) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username + "@163.com", password, getName());
            return simpleAuthenticationInfo;
        } else {
            throw new AuthenticationException();
        }
    }
}
