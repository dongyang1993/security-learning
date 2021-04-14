package com.example.jwt.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserRole {

    private Long id;
    private Long userId;
    private Long roleId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
