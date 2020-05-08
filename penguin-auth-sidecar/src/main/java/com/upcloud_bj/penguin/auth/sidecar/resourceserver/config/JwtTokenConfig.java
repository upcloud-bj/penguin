package com.upcloud_bj.penguin.auth.sidecar.resourceserver.config;

import com.upcloud_bj.penguin.auth.sidecar.resourceserver.properties.PenguinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Jwt Token相关配置
 */
@Configuration
public class JwtTokenConfig {

    @Autowired
    private PenguinProperties penguinProperties;

    @Bean
    public TokenStore tokenStore() {
        // 基于 Jwt 实现
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(penguinProperties.getOauth2().getJwtTokenSigningKey());
        return jwtAccessTokenConverter;
    }
}
