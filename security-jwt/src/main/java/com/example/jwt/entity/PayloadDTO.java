package com.example.jwt.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayloadDTO {
    //主题
    private String sub;
    //签发人
    private String iss;
    //JWT的ID
    private String jti;
    //用户名称
    private String username;
    //受众
    private String aud;
    //签发时间
    private Long iat;
    //生效时间
    private String nbf;
    //过期时间
    private Long exp;
}
