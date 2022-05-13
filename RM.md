### **第二章 身份验证**

* ####纯配置文件
  ini文件（存放身份）

```ini
  [users]
  zhang=123456
  wang=123
```

通过IniSecurityManagerFactory获取ini文件配置

```java
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
```

####自定义域
自定义身份认证规则的类MyRealm1

```java
public class MyRealm1 implements Realm {
    @Override
    public String getName() {
        return MyRealm1.class.getName();
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
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
            return simpleAuthenticationInfo;
        } else {
            throw new AuthenticationException();
        }
    }
}
```

实现接口Realm，将身份认证规则（UsernamePasswordToken）是现在方法***getAuthenticationInfo***中
配置配置文件shiro_config.ini

```ini
myRealm1=com.github.Yimizhao.shiro.chapter2.realm.MyRealm1
securityManager.realms=$myRealm1
```

1.指定自定义Realm（myRealm1）路径
2.将myRealm注入securityManager中
####JDBC认证
配置文件shiro-jdbc-realm.ini配置JDBC链接

```ini
datasource=org.springframework.jdbc.datasource.DriverManagerDataSource
datasource.driverClassName=com.mysql.cj.jdbc.Driver
datasource.url=jdbc:mysql://localhost:3306/white_jotter?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL
datasource.username=root
datasource.password=此处放自己的DB密码
```

配置认证信息

```ini
jdbcRealm=org.apache.shiro.realm.jdbc.JdbcRealm
jdbcRealm.dataSource=$datasource
securityManager.realms=$jdbcRealm
```

1.采用org.apache.shiro.realm.jdbc.JdbcRealm认证
2.将自己注入datasourcejdbcRealm中
3.将自定义Realm（jdbcRealm）注入securityManager中
创建表’users‘

```sql
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb3
```

身份信息存放在表中
