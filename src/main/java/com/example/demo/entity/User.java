package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    @TableField("username")
    private String username;
    @TableField
    private String password; // BCrypt加密存储
    @TableField
    private String email;
    @TableField
    private String phone;
}
