package com.example.jwt.dao;

import com.example.jwt.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleDao {

    List<SysRole> listByUsername(@Param("username") String username);

    List<String> listCodeByUsername(@Param("username") String username);
}
