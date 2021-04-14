package com.example.jwt.dao;

import com.example.jwt.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao {

    SysUser getByUsername(@Param("username") String username);
}
