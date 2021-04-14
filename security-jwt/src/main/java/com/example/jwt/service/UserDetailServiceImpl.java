package com.example.jwt.service;

import com.example.jwt.dao.SysPermissionDao;
import com.example.jwt.dao.SysRoleDao;
import com.example.jwt.dao.SysUserDao;
import com.example.jwt.dao.SysUserRoleDao;
import com.example.jwt.entity.SysPermission;
import com.example.jwt.entity.SysRole;
import com.example.jwt.entity.SysUser;
import com.example.jwt.entity.SysUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
        List<String> roles = sysRoleService.listCodeByUsername(username);
        List<String> permissions = sysPermissionService.listPathBySysUsername(username);
//        if (!CollectionUtils.isEmpty(permissions)) {
//            authorityList.addAll(AuthorityUtils.createAuthorityList(permissions.toArray(String[]::new)));
//        }
        return User.builder().username(sysUser.getUsername()).password(sysUser.getPassword()).roles(roles.toArray(String[]::new)).authorities(permissions.toArray(String[]::new)).build();
    }
}
