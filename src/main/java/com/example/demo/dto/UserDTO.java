package com.example.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "APP端登录实体")
public class UserDTO {
    @Schema(description = "用户昵称")
    private String username;

    @Schema(description = "手机号码")
    private String phone;

//    @Schema(description = "creationTime")
//    private String createTime;

}