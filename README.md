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



# Penguin Auth Sidecar 使用说明

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

**说明：**

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