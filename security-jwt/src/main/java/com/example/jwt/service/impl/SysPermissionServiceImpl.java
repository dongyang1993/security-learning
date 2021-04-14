package com.example.jwt.service.impl;

import com.example.jwt.dao.SysPermissionDao;
import com.example.jwt.entity.SysPermission;
import com.example.jwt.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    public List<SysPermission> listBySysUsername(String username) {
        return sysPermissionDao.listBySysUsername(username);
    }

    @Override
    public List<String> listPathBySysUsername(String username) {
        return null;
    }
}
