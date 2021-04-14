package com.example.jwt.service;

import com.example.jwt.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        List<String> authorityList = new ArrayList<>();
        List<String> roles = sysRoleService.listCodeByUsername(username);
        if (!CollectionUtils.isEmpty(roles)) {
            authorityList.addAll(roles.stream().map(n -> "ROLE_" + n).collect(Collectors.toList()));
        }
        List<String> permissions = sysPermissionService.listPathBySysUsername(username);
        if (!CollectionUtils.isEmpty(permissions)) {
            authorityList.addAll(permissions);
        }
        return User.builder().username(sysUser.getUsername()).password(sysUser.getPassword()).authorities(authorityList.toArray(String[]::new)).build();
    }
}
