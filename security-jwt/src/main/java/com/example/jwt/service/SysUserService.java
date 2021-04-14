package com.example.jwt.service;

import com.example.jwt.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserService {
    SysUser getByUsername(@Param("username") String username);
}
