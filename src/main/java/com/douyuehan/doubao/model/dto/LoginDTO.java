package com.douyuehan.doubao.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO和客户端发来的请求一一对应
 */

@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空") //@NotBlank：只用于String，不能为null且trim()之后size>0
    @Size(min = 2, max = 15, message = "登录用户名长度在2-15") //@Size：验证对象长度是否在给定的范围之内
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "登录密码长度在6-20")
    private String password;

    private Boolean rememberMe;

}
