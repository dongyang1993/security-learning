package com.example.jwt.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysRole {

    private Long id;
    private String name;
    private String code;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
