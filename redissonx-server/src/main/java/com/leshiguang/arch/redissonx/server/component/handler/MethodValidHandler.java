package com.leshiguang.arch.redissonx.server.component.handler;

import com.alibaba.fastjson.JSON;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxResponseErrorCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 14:07
 * @Modify
 */
public class MethodValidHandler {
    /**
     * 分隔符
     */
    private static final String SEPARATOR = ",";

    /**
     * 拦截数据校验异常
     *
     * @param request 请求
     * @param e       校验异常
     * @return 通用返回格式
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RedissonxResponse notValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMsgList = new ArrayList();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsgList.add(fieldError.getDefaultMessage());
        }
        return RedissonxResponseBuilder.fail(RedissonxResponseErrorCode.PARAM_ERROR.getErrorCode(), JSON.toJSONString(errorMsgList));
    }
}
