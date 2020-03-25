package com.leshiguang.arch.redissonx.server.controller.common;

import com.leshiguang.arch.cas.support.service.LogoutService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 11:43
 * @Modify
 */
@Controller
public class IndexController {
    @Resource
    private LogoutService logoutService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletResponse res) {
        try {
            res.sendRedirect("/redissonx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    @ResponseBody
    public RedissonxResponse echo(@RequestParam String requestId) {
        return RedissonxResponseBuilder.success(true);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        logoutService.logout(req, res);
    }
}
