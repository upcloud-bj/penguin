package com.upcloud_bj.penguin.auth.server.service;

import com.upcloud_bj.penguin.ums.entity.SysPermission;
import com.upcloud_bj.penguin.ums.entity.SysRole;
import com.upcloud_bj.penguin.ums.entity.SysUser;
import com.upcloud_bj.penguin.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Map<String, Object> userInfo = userService.getUserWithPermissionsByName(userName);

        if (userInfo != null) {
            SysUser user = ((SysUser) userInfo.get("user"));
            List<SysRole> roles = (List<SysRole>) userInfo.get("roles");
            List<SysPermission> permissions = (List<SysPermission>) userInfo.get("permissions");

            List<String> roleNames = new ArrayList<String>();
            for (SysRole role : roles) {
                roleNames.add(role.getEnName());
            }

            List<String> permissionNames = new ArrayList<String>();
            for (SysPermission permission : permissions) {
                permissionNames.add(permission.getEnName());
            }

            return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(roleNames.toArray(new String[]{}))
                    .authorities(permissionNames.toArray(new String[]{}))
                    .build();
        }
        return null;
    }
}
