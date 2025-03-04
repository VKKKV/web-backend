package com.example.demo.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "标准响应格式")
public class ResponseResult<T> {
    @Schema(description = "状态码", example = "200")
    private Integer code;
    @Schema(description = "返回数据")
    private T data;
    @Schema(description = "提示信息", example = "操作成功")
    private String message;

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(200);
        result.setData(data);
        result.setMessage("操作成功");
        return result;
    }
}
