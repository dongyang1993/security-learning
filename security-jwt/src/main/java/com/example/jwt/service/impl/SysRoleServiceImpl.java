package com.example.jwt.service.impl;

import com.example.jwt.dao.SysRoleDao;
import com.example.jwt.entity.SysRole;
import com.example.jwt.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> listByUsername(String username) {
        return sysRoleDao.listByUsername(username);
    }

    @Override
    public List<String> listCodeByUsername(String username) {
        return sysRoleDao.listCodeByUsername(username);
    }
}
