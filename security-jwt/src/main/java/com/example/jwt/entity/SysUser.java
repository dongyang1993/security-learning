package com.example.jwt.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {

    private Long id;
    private String username;
    private String password;
    private Long status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
