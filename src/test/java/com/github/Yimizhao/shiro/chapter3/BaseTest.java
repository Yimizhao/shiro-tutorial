package com.github.Yimizhao.shiro.chapter3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;

public abstract class BaseTest {

    @After
    public void tearDown() {
        ThreadContext.unbindSubject();
    }

    protected void login(String iniFile, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + iniFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
            subject().login(token);
        } catch (AuthenticationException e) {
            System.out.println("登录失败");
            e.printStackTrace();
        }
    }

    protected Subject subject() {
        return SecurityUtils.getSubject();
    }
}
