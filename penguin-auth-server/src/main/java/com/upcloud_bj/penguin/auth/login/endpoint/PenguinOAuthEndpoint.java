package com.upcloud_bj.penguin.auth.login.endpoint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "penguin-oauth", url = "${oauth.endpoint}")
public interface PenguinOAuthEndpoint {

    @PostMapping("/oauth/token")
    Map<String, Object> oauthToken(
            @RequestParam(value = "grant_type") String grant_type,
            @RequestParam(value = "client_id") String client_id,
            @RequestParam(value = "client_secret") String client_secret,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "refresh_token") String refresh_token);
}
