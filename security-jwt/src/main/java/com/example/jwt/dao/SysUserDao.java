package com.example.jwt.dao;

import com.example.jwt.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserDao {

    SysUser getByUsername(@Param("username") String username);
}
