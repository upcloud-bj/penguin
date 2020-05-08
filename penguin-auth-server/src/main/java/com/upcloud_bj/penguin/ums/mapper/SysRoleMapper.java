package com.upcloud_bj.penguin.ums.mapper;

import com.upcloud_bj.penguin.common.mybatis.mapper.IMapper;
import com.upcloud_bj.penguin.ums.entity.SysRole;

import java.util.List;

public interface SysRoleMapper extends IMapper<SysRole> {
    List<SysRole> selectUserRolesByUserId(Integer userId);
}