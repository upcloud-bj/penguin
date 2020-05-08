package com.upcloud_bj.penguin.ums.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upcloud_bj.penguin.ums.entity.SysPermission;
import com.upcloud_bj.penguin.ums.entity.SysRole;
import com.upcloud_bj.penguin.ums.entity.SysUser;
import com.upcloud_bj.penguin.ums.mapper.SysPermissionMapper;
import com.upcloud_bj.penguin.ums.mapper.SysRoleMapper;
import com.upcloud_bj.penguin.ums.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    public PageInfo<SysUser> getUsersByPage(SysUser user, Integer page, Integer size) {

        Example e = new Example(SysUser.class);
        Example.Criteria c = e.createCriteria();
        if (user.getId() != null) {
            c.andEqualTo("id", user.getId());
        }
        String userName = user.getUserName();
        if (!StringUtils.isEmpty(userName)) {
            c.andEqualTo("userName", userName);
        }
        String name = user.getName();
        if (!StringUtils.isEmpty(name)) {
            c.andLike("name", String.format("%%s%", name));
        }
        String comment = user.getComment();
        if (!StringUtils.isEmpty(comment)) {
            c.andLike("comment", String.format("%%s%", comment));
        }
        String status = user.getStatus();
        if (!StringUtils.isEmpty(status)) {
            c.andEqualTo("status", status);
        }

        PageHelper.startPage(page == null ? 0 : page, size == null ? 5 : size);

        return PageInfo.of(userMapper.selectByExample(e));
    }


    public SysUser getUserByUserName(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("userName", userName)
                .andEqualTo("status", "00");
        return ((SysUser) userMapper.selectOneByExample(example));
    }

    public Map<String, Object> getUserWithPermissionsByName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        Example userExample = new Example(SysUser.class);
        userExample
                .createCriteria()
                .andEqualTo("userName", userName)
                .andEqualTo("status", "00");
        SysUser sysUser = (SysUser) userMapper.selectOneByExample(userExample);
        if (sysUser == null) {
            return null;
        }

        Integer userId = sysUser.getId();

        List<SysRole> sysRoles = roleMapper.selectUserRolesByUserId(userId);
        List<SysPermission> sysPermissions = permissionMapper.selectPermissionsByUserId(userId);

        HashMap<String, Object> rstMap = new HashMap<String, Object>();
        rstMap.put("user", sysUser);
        rstMap.put("roles", sysRoles);
        rstMap.put("permissions", sysPermissions);

        return rstMap;
    }
}
