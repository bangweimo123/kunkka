package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.redissonx.server.domain.connect.ConnectQueryReq;
import com.leshiguang.arch.redissonx.server.domain.connect.ConnectVO;
import com.leshiguang.arch.redissonx.server.domain.request.ConnectQueryRequest;
import com.leshiguang.arch.redissonx.server.service.ConnectService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:14
 * @Modify
 */
@RestController
@Slf4j
public class ConnectController {
    @Resource
    private ConnectService connectService;
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/api/connect/save")
    public RedissonxResponse save(@RequestBody ConnectVO connect) {
        return connectService.save(connect, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/connect/delete/{connectName}")
    public RedissonxResponse delete(@PathVariable String connectName) {
        return connectService.delete(connectName, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/connect/loadRelationClusters/{connectName}")
    public RedissonxResponse loadRelationClusters(@PathVariable String connectName) {
        return connectService.loadRelationClusters(connectName);
    }

    @PostMapping("/api/connect/query")
    public RedissonxResponse query(@RequestBody ConnectQueryReq request) {
        ConnectQueryRequest queryRequest = new ConnectQueryRequest();
        queryRequest.setKeyword(request.getKeyword());
        queryRequest.setRegion(request.getRegion());
        queryRequest.setAddress(request.getAddress());
        return connectService.query(queryRequest, request.getPaging());
    }
}
