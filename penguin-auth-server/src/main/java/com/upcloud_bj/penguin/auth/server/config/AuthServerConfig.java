package com.upcloud_bj.penguin.auth.server.config;

import com.upcloud_bj.penguin.auth.server.service.UserDetailsServiceImp;
import com.upcloud_bj.penguin.ums.entity.SysUser;
import com.upcloud_bj.penguin.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private ClientDetailsService jdbcClientDetails;

    @Autowired
    private UserService userService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 读取客户端配置
        clients.withClientDetails(jdbcClientDetails);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        List<TokenEnhancer> list = new ArrayList<TokenEnhancer>();
        list.add(sessionInfoTokenEnhancer());
        list.add(jwtAccessTokenConverter);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(list);

        endpoints.authenticationManager(authenticationManager)
                // 设置jwt
                .tokenStore(tokenStore).tokenEnhancer(tokenEnhancerChain)
                // UserDetailsService
                .userDetailsService(userDetailsServiceImp);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Bean
    public TokenEnhancer sessionInfoTokenEnhancer() {
        return new TokenEnhancer() {
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
                Map<String, Object> sessionMap = new HashMap<String, Object>();
                OAuth2Request request = oAuth2Authentication.getOAuth2Request();

                if (request.isRefresh()) {
                    // 刷新token模式
                    String grantType = request.getRefreshTokenRequest().getGrantType();
                    if ("refresh_token".equals(grantType)) {
                        String userName = oAuth2Authentication.getUserAuthentication().getName();
                        if (!StringUtils.isEmpty(userName)) {
                            SysUser user = userService.getUserByUserName(userName);
                            sessionMap.put("userId", user.getId());
                            sessionMap.put("userName", user.getUserName());
                            sessionMap.put("name", user.getName());
                            sessionMap.put("headerImg", user.getHeaderImg());
                        }
                    }
                } else {
                    // 获取token模式
                    String grantType = request.getGrantType();
                    if ("password".equals(grantType)) {
                        // 密码模式
                        String userName = oAuth2Authentication.getUserAuthentication().getName();
                        if (!StringUtils.isEmpty(userName)) {
                            SysUser user = userService.getUserByUserName(userName);
                            sessionMap.put("userId", user.getId());
                            sessionMap.put("userName", user.getUserName());
                            sessionMap.put("name", user.getName());
                            sessionMap.put("headerImg", user.getHeaderImg());
                        }
                    } else if ("client_credentials".equals(grantType)) {
                        // TODO: 客户端模式
                    }
                }

                // TODO：其他session信息
                // sessionMap.put("aaa", "bbb");

                Map<String, Object> additionalMap = new HashMap<String, Object>();
                additionalMap.put("session", sessionMap);

                ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalMap);

                return oAuth2AccessToken;
            }
        };
    }
}