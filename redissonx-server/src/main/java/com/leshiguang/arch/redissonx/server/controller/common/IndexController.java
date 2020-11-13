package com.leshiguang.arch.redissonx.server.controller.common;

import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 11:43
 * @Modify
 */
@RestController
public class IndexController {

    @GetMapping(value = "/")
    public void index(HttpServletResponse res) {
        try {
            res.sendRedirect("/redissonx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/echo")
    @ResponseBody
    public RedissonxResponse echo(@RequestParam(required = false) String requestId) {
        return RedissonxResponseBuilder.success(requestId);
    }
}
