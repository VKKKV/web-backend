package com.example.demo.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 封装登录用户的信息。
 */
@Data
@AllArgsConstructor
public class LoginUser {
    private Long userId;
    private String username;
}
