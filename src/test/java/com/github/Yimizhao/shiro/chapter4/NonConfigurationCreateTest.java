package com.github.Yimizhao.shiro.chapter4;

import com.github.Yimizhao.shiro.chapter3.permission.BitAndWildPermissionResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Arrays;

public class NonConfigurationCreateTest {

    @Test
    public void testNonConfigurationCreateTest() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 定义验证
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        // 验证策略
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        // 定义权限访问控制
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new BitAndWildPermissionResolver());

        securityManager.setAuthenticator(authenticator);
        securityManager.setAuthorizer(authorizer);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/white_jotter?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL");
        dataSource.setUsername("root");
        dataSource.setPassword("zym@1205");
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setPermissionsLookupEnabled(Boolean.TRUE);
        jdbcRealm.setDataSource(dataSource);
        securityManager.setRealms(Arrays.asList(jdbcRealm));
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhao", "123456");
        try {
            subject.login(token);
            Assert.assertTrue(subject.isAuthenticated());
            Assert.assertTrue(subject.hasRole("role1"));
            Assert.assertTrue(subject.hasRole("role2"));
            Assert.assertTrue(subject.hasRole("role3"));
            Assert.assertFalse(subject.hasRole("role4"));
            Assert.assertTrue(subject.isPermitted("user1:*"));
            Assert.assertTrue(subject.isPermitted("+user1+10"));
            Assert.assertTrue(subject.isPermitted("+user2+10"));
            Assert.assertTrue(subject.isPermitted("user2:*"));
        } catch (AuthenticationException e) {
            System.out.println("登录失败");
            e.printStackTrace();
        } finally {
            subject.logout();
        }
    }
}
