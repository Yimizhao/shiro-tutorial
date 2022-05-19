package com.github.Yimizhao.shiro.chapter3;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RoleTest extends BaseTest {

    @Test
    public void testHasRole() {
        login("chapter3/shiro-role.ini", "zhang", "123");
        Assert.assertTrue(subject().hasRole("role1"));
        Assert.assertTrue(subject().hasRole("role2"));
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(result[0]);
        Assert.assertTrue(result[1]);
        Assert.assertFalse(result[2]);
        for (boolean role : result) {
            System.out.println(role);
        }
        subject().logout();
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("chapter3/shiro-role.ini", "zhao", "123");
        subject().checkRole("role1");
        subject().checkRole("role2");
        subject().checkRole("role3");
        subject().checkRole("role4");
        subject().checkRoles("role1", "role2", "role3");
        subject().checkRoles(Arrays.asList("role1", "role2", "role3", "role4"));
    }

    @Test
    public void testHasRole_jdbc() {
        login("chapter3/shiro-jdbc-role.ini", "zhao", "123456");
        Assert.assertTrue(subject().hasRole("role1"));
        Assert.assertTrue(subject().hasRole("role2"));
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(result[0]);
        Assert.assertTrue(result[1]);
        Assert.assertTrue(result[2]);
        for (boolean role : result) {
            System.out.println(role);
        }
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole_jdbc() {
        login("chapter3/shiro-jdbc-role.ini", "zhao", "123456");
        subject().checkRole("role1");
        subject().checkRole("role2");
        subject().checkRole("role3");
        subject().checkRoles("role3", "role4");
    }

    @Test
    public void testHasRolePermission() {
        login("chapter3/shiro-permission.ini", "zhao", "123456");
        Assert.assertTrue(subject().isPermitted("user:create"));
        Assert.assertTrue(subject().isPermitted("user:delete"));
        Assert.assertTrue(subject().isPermitted("user:update"));
        Assert.assertFalse(subject().isPermitted("user:select"));
//        Assert.assertTrue(subject().isPermitted("user:select"));
    }

    @Test(expected = AuthorizationException.class)
    public void testCheckRolePermission() {
        login("chapter3/shiro-permission.ini", "zhao", "123456");
        subject().checkPermission("user:create");
        subject().checkPermission("user:delete");
        subject().checkPermission("user:update");
        // 无“user:select”权限，抛AuthorizationException异常
        subject().checkPermission("user:select");
    }

    @Test
    public void name() {
//        String s = "+资源字符串+权限位+实例ID";
        String s = "++";
        String[] ss = s.split("\\+");
        System.out.println(ss.length);
        for (String s1 : ss) {
            System.out.println("s1: " + s1);
        }
    }
}
