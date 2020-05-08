package com.upcloud_bj.penguin.auth.login.controller;

import com.upcloud_bj.penguin.auth.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/web/login")
    public Map<String, Object> loginWeb(String userName, String password) throws Exception {
        return authService.loginWeb(userName, password);
    }

    @PostMapping("/web/refreshToken")
    public Map<String, Object> refreshTokenWeb(String refreshToken) throws Exception {
        return authService.refreshTokenWeb(refreshToken);
    }
}
