package com.example.jwt.dao;

import com.example.jwt.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionDao {
    List<SysPermission> listBySysUsername(@Param("username") String username);

    List<String> listPathBySysUsername(@Param("username") String username);
}
