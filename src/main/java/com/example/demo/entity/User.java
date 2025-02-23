package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("users")
public class User {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    @TableField("username")
    private String username;
    @TableField("password_hash")
    private String password; // BCrypt加密存储
    @TableField("email")
    private String email;
    @TableField("phone")
    private String phone;
    @TableField(fill = FieldFill.INSERT)
    private Instant createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private Instant updatedAt;

    @TableLogic(value = "0", delval = "1")  // 声明逻辑删除注解
    @TableField("is_deleted")               // 映射数据库字段名
    private Integer isDeleted;              // 0-未删除，1-已删除
}
