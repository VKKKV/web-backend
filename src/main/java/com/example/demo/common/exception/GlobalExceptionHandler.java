package com.example.demo.common.exception;

import com.example.demo.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

//统一异常处理，全局异常处理器error。
@ControllerAdvice //用于声明处理全局Controller方法异常的类
public class GlobalExceptionHandler {

    /**
     * 全局异常处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class) //用于声明处理异常的方法，`value`属性用于声明该方法处理的异常类型
    @ResponseBody //表示将方法的返回值作为HTTP的响应体
    public Result error(Exception e) {
        //控制台打印异常信息。
        e.printStackTrace();
        //返回失败结果给前端。
        return Result.fail();//发生异常时的返回值。
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String,Object>> handleServiceException(ServiceException e) {
        return ResponseEntity.status(500)
                .body(Map.of(
                        "error", e.getMessage(),
                        "detail", e.getCause().getMessage()
                ));
    }

    /**
     * LeaseException异常发生时的处理方法。
     * 业务逻辑是阐述公寓时，还有剩余房间，则捕获异常并返回提示信息。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TradeException.class)
    @ResponseBody
    public Result error(TradeException e) {
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMessage());
    }
}
