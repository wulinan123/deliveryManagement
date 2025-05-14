package com.sky.dto;


import lombok.Data;

/**
 * PasswordEditDTO
 */
@Data
public class EmployeeEditPasswordDTO {
    /**
     * 员工id
     */
    private long empId;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 旧密码
     */
    private String oldPassword;

}

