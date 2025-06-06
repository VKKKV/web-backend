package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @TableName users
 */
@TableName(value = "users")

@Data
@Schema(description = "用户实体")
public class Users {
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户唯一ID", example = "1001")
    private Integer userId;

    @Schema(description = "用户登录名", example = "john_doe", maxLength = 50)
    private String username;

    @Schema(description = "密码", example = "123", accessMode =
            Schema.AccessMode.WRITE_ONLY)
//    @TableField( value = "password_hash", select = false)
    @TableField( value = "password_hash")
    private String password;

    @Schema(description = "用户邮箱", example = "user@example.com", format = "email")
    private String email;

    @Schema(description = "电话号码", example = "13812345678")
    private String phone;

    @Schema(description = "账户余额", example = "10000.00")
    private BigDecimal balance;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

    @Schema(description = "删除状态: 0-正常, 1-删除", allowableValues = { "0", "1" })
    private Integer isDeleted;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Users other = (Users) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getUsername() == null ? other.getUsername() == null
                        : this.getUsername().equals(other.getUsername()))
                && (this.getPassword() == null ? other.getPassword() == null
                        : this.getPassword().equals(other.getPassword()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getBalance() == null ? other.getBalance() == null
                        : this.getBalance().equals(other.getBalance()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null
                        : this.getCreatedAt().equals(other.getCreatedAt()))
                && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null
                        : this.getUpdatedAt().equals(other.getUpdatedAt()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null
                        : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", userId=" + userId +
                ", username=" + username +
                ", passwordHash=" + password +
                ", email=" + email +
                ", phone=" + phone +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                "]";
    }
}