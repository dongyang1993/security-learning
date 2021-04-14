package com.example.jwt.service;

import com.example.jwt.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionService {

    List<SysPermission> listBySysUsername(@Param("username") String username);

    List<String> listPathBySysUsername(@Param("username") String username);
}
