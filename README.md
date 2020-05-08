[toc]

# Penguin

## 说明

Penguin是由升云科技（北京）有限公司实现的基于Springboot的OAuth2.0实现。

具有以下主要特点：

- JWT的token以及自定义token内容
- 将资源服务器的权限管理抽出为penguin-auth-sidecar，方便各资源服务直接引用jar配合appliaction.yml中的权限设置进行细粒度的权限控制。
- 实现基本的用户账户管理业务（ums）
- 实现方便http请求的登录服务（http://host:port/auth/web/login)
- 集成健康监测服务（penguin-health）
- 微服务化，方便集成到各种微服务环境中（k8s，istio）

## 版权信息

升云科技（北京）有限公司版权所有



# Penguin Auth Server 认证授权服务（OAuth2.0）

## application.yml

```yaml
server:
  port: 8080
spring:
  application:
    name: auth-server
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://192.168.1.130:31306/sys_auth?useUnicode=true&characterEncoding=utf-8&useSSL=false
      username: root
      password: upcloud-orgms
      initial-size: 1
      min-idle: 1
      max-active: 20
      test-on-borrow: true
  boot:
    admin:
      client:
        url: http://localhost:8099
        username: upcloud
        password: upcloud
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
mybatis:
  type-aliases-package: com.upcloud_bj.penguin.ums.entity
  mapper-locations: classpath:mapper/*.xml

oauth:
  endpoint: http://localhost:8080
  clients:
    - name: web
      client-id: democlient
      client-secret: secret

penguin:
  oauth2:
    jwt-token-signing-key: penguin-123
  resourceserver:
    permitUris:
      - uri: /actuator/**
        methods:
          - ALL
      - uri: /auth/web/login
        methods:
          - POST
      - uri: /auth/web/refreshToken
        methods:
          - POST
    permissions:
      - en-name: UMS_VIEW
        uri: /ums/**
        methods:
          - GET
      - en-name: UMS_EDIT
        uri: /ums/**
        methods:
          - ALL
```

> 根据需要修改端口、数据库连接配置、mybatis配置、健康服务地址/账号/密码、资源服务器权限配置

## 数据库DDL

参考：penguin/01.documents/01.db/01.ddl/ddl.sql

## 测试数据

参考：penguin/01.documents/01.db/02.dummy-data/Dump20200424.sql

HTTP登录URL：http://localhost:8080/auth/web/login （POST）

登录账号：admin

登录密码：123



# Penguin Auth Sidecar 资源服务器权限管理

## 在资源工程的pom中引入penguin-auth-sidecar依赖

```xml
<dependency>
    <groupId>com.upcloud-bj</groupId>
    <artifactId>penguin-auth-sidecar</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 配置资源权限

application.yml

```yaml
penguin:
  oauth2:
    # jwt密钥
    jwt-token-signing-key: penguin-123
  resourceserver:
    # 不进行权限控制的请求uri
    permitUris:
      - uri: /auth/web/login
        methods:
          - POST
      - uri: /auth/web/refreshToken
        methods:
          - POST
    # 需要具备权限的请求uri
    permissions:
      # 权限名称
      - en-name: UMS_VIEW
        # 拦截的uri（AntPath）
        uri: /ums/**
        # 拦截的http方法
        methods:
          # GET、POST、PUT、DELETE、ALL
          - GET
      - en-name: UMS_EDIT
        uri: /ums/**
        methods:
          - ALL
```

**说明：**参考注释

## 在入口类中追加扫描路径

```java
package com.upcloud_bj.penguin.auth.server;
 
@ServletComponentScan(basePackages = "com.upcloud_bj.penguin.auth.sidecar.resourceserver.filter")
@ComponentScan(basePackages = {"com.upcloud_bj.penguin", "com.upcloud_bj.penguin.auth.sidecar.resourceserver"})
@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
```

主要注意以下两个注解：

1. @ServletComponentScan(basePackages = "com.upcloud_bj.penguin.auth.sidecar.resourceserver.filter")

   说明：

   - **固定引用**
   - 需要使用tokenSession时引入，不需要不用引入。具体使用方法参考下面【4】。

2. @ComponentScan(basePackages = {"com.upcloud_bj.penguin", "com.upcloud_bj.penguin.auth.sidecar.resourceserver"})

   说明：

     - "com.upcloud_bj.penguin"

       指定为自己工程Application类的包名

     - "com.upcloud_bj.penguin.auth.sidecar.resourceserver"

       **固定引用**

3. 在Controller中获取tokenSession数据

   ```java
   @RequestAttribute Map<String, Object> tokenSession
   ```

   session中的信息：

   - userId

     用户ID

   - userName

     用户名（登录账号）

   - name

     用户名称

   - headerImg

     头像

# Penguin Health 健康监测服务

## application.yml

```yaml
server:
  port: 8099
spring:
  application:
    name: health
  security:
    user:
      name: upcloud
      password: upcloud
```

> 根据需要修改端口和账号密码