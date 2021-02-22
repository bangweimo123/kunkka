package com.leshiguang.arch.kunkka.web.config;

import com.leshiguang.arch.kunkka.web.exception.ServerErrorCode;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author bangwei.mo
 * @Date 2021-01-18 12:59
 * @Description
 */
@ControllerAdvice
public class KunkkaExceptionHandler {

    @ExceptionHandler(KunkkaException.class)
    @ResponseBody
    public ResultData errorHandler(KunkkaException e) {
        return ResultDataBuilder.failWithNull(e.getMessage(), e.getErrorCode().getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData errorHandler(Exception e) {
        return ResultDataBuilder.failWithNull(ServerErrorCode.SYSTEM_ERROR.getMsg(), ServerErrorCode.SYSTEM_ERROR.getCode());
    }
}
