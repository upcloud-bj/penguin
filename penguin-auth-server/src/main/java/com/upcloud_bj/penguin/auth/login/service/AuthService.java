package com.upcloud_bj.penguin.auth.login.service;

import com.alibaba.fastjson.JSON;
import com.upcloud_bj.penguin.auth.login.endpoint.PenguinOAuthEndpoint;
import com.upcloud_bj.penguin.auth.login.properties.OAuthProperties;
import com.upcloud_bj.penguin.ums.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private PenguinOAuthEndpoint polestarOAuthEndpoint;

    @Autowired
    private OAuthProperties oAuthProperties;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Map<String, Object> loginWeb(String userName, String password) throws Exception {
        OAuthProperties.Client client = this.getClientInfo("web");
        if (client == null) {
            throw new Exception("客户端信息不存在！");
        }
        return polestarOAuthEndpoint.oauthToken("password", client.getClientId(), client.getClientSecret(), userName, password, null);
    }

    public Map<String, Object> refreshTokenWeb(String refreshToken) throws Exception {
        OAuthProperties.Client client = this.getClientInfo("web");
        if (client == null) {
            throw new Exception("客户端信息不存在！");
        }
        return polestarOAuthEndpoint.oauthToken("refresh_token", client.getClientId(), client.getClientSecret(), null, null, refreshToken);
    }

    private OAuthProperties.Client getClientInfo(String clientName) {
        for (OAuthProperties.Client client : oAuthProperties.getClients()) {
            if (clientName.equals(client.getName())) {
                return client;
            }
        }
        return null;
    }
}
