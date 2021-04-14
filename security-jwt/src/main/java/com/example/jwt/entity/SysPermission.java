package com.example.jwt.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysPermission {

    private Long id;
    private String name;
    private String tag;
    private String path;
    private String method;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
