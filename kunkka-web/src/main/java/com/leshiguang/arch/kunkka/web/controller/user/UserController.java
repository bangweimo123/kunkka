package com.leshiguang.arch.kunkka.web.controller.user;


import com.leshiguang.arch.cas.support.entity.LoginUserInfoEntity;
import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.kunkka.web.controller.application.ApiController;
import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 14:16
 * @Modify
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends ApiController {
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("userinfo")
    public ResultData<LoginUserInfoEntity> userinfo() {
        LoginUserInfoEntity loginUserInfoEntity = userInfoService.fetchLoginUser();
        if (null == loginUserInfoEntity || StringUtils.isBlank(loginUserInfoEntity.getUserId())) {
            loginUserInfoEntity = new LoginUserInfoEntity();
            loginUserInfoEntity.setUserId("bangwei.mo");
            loginUserInfoEntity.setName("莫邦伟");
            loginUserInfoEntity.setEmail("bangwei.mo@lifesense.com");
        }
        return ResultDataBuilder.success(loginUserInfoEntity);
    }
}
