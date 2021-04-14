package com.example.jwt.service;

import com.example.jwt.entity.SysRole;

import java.util.List;

public interface SysRoleService {
    List<SysRole> listByUsername(String username);

    List<String> listCodeByUsername(String username);
}
