package com.example.demo.common.exception;

import com.example.demo.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class TradeException extends RuntimeException {

    //异常状态码
    private Integer code;
    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public TradeException(String message, Integer code) {
        super(message);//父类构造器
        this.code = code;
    }

    /**
     * 根据响应结果枚举对象创建异常对象
     * @param resultCodeEnum
     */
    public TradeException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "TradeException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
