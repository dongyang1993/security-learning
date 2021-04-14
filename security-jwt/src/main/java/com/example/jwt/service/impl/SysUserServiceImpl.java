package com.example.jwt.service.impl;

import com.example.jwt.dao.SysUserDao;
import com.example.jwt.entity.SysUser;
import com.example.jwt.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserDao.getByUsername(username);
    }
}
