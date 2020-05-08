package com.upcloud_bj.penguin.auth.sidecar.resourceserver.config;

import com.upcloud_bj.penguin.auth.sidecar.resourceserver.properties.PenguinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private PenguinProperties penguinProperties;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg = http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests();

        // 不需要权限
        List<PenguinProperties.PermitUri> permitUris = penguinProperties.getResourceserver().getPermitUris();
        if (permitUris != null) {
            for (PenguinProperties.PermitUri permitUri : permitUris) {
                String uri = permitUri.getUri();
                List<String> methods = permitUri.getMethods();

                for (String method : methods) {
                    if ("ALL".equals(method)) {
                        reg.antMatchers(uri).permitAll();
                    } else {
                        reg.antMatchers(HttpMethod.valueOf(method.toUpperCase()), uri).permitAll();
                    }
                }
            }
        }

        // 需要权限
        List<PenguinProperties.Permission> permissions = penguinProperties.getResourceserver().getPermissions();
        if (permissions != null) {
            for (PenguinProperties.Permission permission : permissions) {
                String enName = permission.getEnName();
                String uri = permission.getUri();
                List<String> methods = permission.getMethods();
                for (String method : methods) {
                    if ("ALL".equals(method)) {
                        reg.antMatchers(uri).hasAuthority(enName);
                    } else {
                        reg.antMatchers(HttpMethod.valueOf(method.toUpperCase()), uri).hasAuthority(enName);
                    }
                }
            }
        }

        reg.anyRequest().denyAll();
    }
}