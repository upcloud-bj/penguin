package com.upcloud_bj.penguin.ums.mapper;

import com.upcloud_bj.penguin.common.mybatis.mapper.IMapper;
import com.upcloud_bj.penguin.ums.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends IMapper<SysPermission> {
    List<SysPermission> selectPermissionsByUserId(Integer userId);
}