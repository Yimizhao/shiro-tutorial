package com.github.Yimizhao.shiro.chapter2;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

;

public class IniFileLoginAndLogoutTest {


    @Test
    public void testIniFileLoginAndLogoutT() {
        // 获取ini文件配置文件（主要获取Realm）
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:chapter2/shrio.ini");
        // 获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        // 全局绑定SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
        // 获取当前subject
        Subject subject = SecurityUtils.getSubject();
        // 用户登录
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang", "123456");
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");

        } catch (AuthenticationException exception) {
            System.out.println("登录失败");
            System.out.println(exception);
        }

        Assert.assertEquals(Boolean.TRUE, subject.isAuthenticated());

        // 推出登录
        subject.logout();
    }

    @Test
    public void testIniFileLoginAndLogoutT_realm() {

        // 引入配置文件（增加自定义域）
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:chapter2/shiro_config.ini");
        // 取得SecurityManager
        SecurityManager securityManager = securityManagerFactory.getInstance();
        // 全局绑定SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
        // 取得当前用户
        Subject subject = SecurityUtils.getSubject();
        // 定义Token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang", "123456");

        try {
            // 首次登录（验证合法）
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            System.out.println("登录失败");

            e.printStackTrace();
        }

        System.out.println("登录成功");

        Assert.assertEquals("登录失败", Boolean.TRUE, subject.isAuthenticated());

        subject.logout();

        Assert.assertEquals("登录失败", Boolean.TRUE, subject.isAuthenticated());

    }
}
