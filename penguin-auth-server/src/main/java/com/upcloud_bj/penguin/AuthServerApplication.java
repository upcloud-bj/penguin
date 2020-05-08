package com.upcloud_bj.penguin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@EnableFeignClients(basePackages = "com.upcloud_bj.penguin.auth.login.endpoint")
@MapperScan(basePackages = "com.upcloud_bj.penguin.ums.mapper")
@ServletComponentScan(basePackages = "com.upcloud_bj.penguin.auth.sidecar.resourceserver.filter")
@ComponentScan(basePackages = {"com.upcloud_bj.penguin", "com.upcloud_bj.penguin.auth.sidecar.resourceserver"})
@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
