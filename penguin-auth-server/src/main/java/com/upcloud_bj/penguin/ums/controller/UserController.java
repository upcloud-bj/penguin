package com.upcloud_bj.penguin.ums.controller;

import com.github.pagehelper.PageInfo;
import com.upcloud_bj.penguin.common.controller.exchange.ResponseCode;
import com.upcloud_bj.penguin.common.controller.exchange.ResponseMessage;
import com.upcloud_bj.penguin.ums.entity.SysUser;
import com.upcloud_bj.penguin.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ums/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseMessage getUsers(SysUser user, Integer page, Integer size) {
        PageInfo<SysUser> usersByPage = userService.getUsersByPage(user, page, size);
        return ResponseMessage.newInstance(ResponseCode.SUCCESS, usersByPage);
    }
}
