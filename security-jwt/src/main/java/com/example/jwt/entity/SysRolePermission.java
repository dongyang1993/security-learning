package com.example.jwt.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * null
 * @TableName t_sys_role_permission
 */
@Data
public class SysRolePermission implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 
     */
    private Long role_id;

    /**
     * 
     */
    private Long permission_id;

    /**
     * 
     */
    private LocalDateTime create_time;

    /**
     * 
     */
    private LocalDateTime update_time;

    private static final long serialVersionUID = 1L;
}