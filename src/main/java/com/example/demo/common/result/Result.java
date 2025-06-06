package com.example.demo.common.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data; //泛型，灵活处理不同的数据类型。

    public Result() {
    }

    //私有 供调用
    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null)
            result.setData(data);
        return result;
    }

    //公开 通用
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    //公开 成功返回
    public static <T> Result<T> success(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    //公开 成功返回 不包含数据
    public static <T> Result<T> success() {
        return Result.success(null);
    }

    //公开 失败 不包含数据
    public static <T> Result<T> fail() {
        return build(null, ResultCodeEnum.FAIL);
    }

    //新增 根据code+message创建返回值
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    //新增 根据ResultCodeEnum创建返回值
    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(null);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }
}