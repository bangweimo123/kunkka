package com.leshiguang.arch.redissonx.server.controller.user;


import com.leshiguang.arch.cas.support.entity.LoginUserInfoEntity;
import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 14:16
 * @Modify
 */
@RestController
public class UserController {
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/api/user/userinfo")
    public RedissonxResponse<LoginUserInfoEntity> userinfo() {
        return RedissonxResponseBuilder.success(userInfoService.fetchLoginUser());
    }
}
