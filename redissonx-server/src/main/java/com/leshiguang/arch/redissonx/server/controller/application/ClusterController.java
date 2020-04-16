package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterQueryReq;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterVO;
import com.leshiguang.arch.redissonx.server.domain.request.ClusterQueryRequest;
import com.leshiguang.arch.redissonx.server.service.ClusterService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:53
 * @Modify
 */
@RestController
@Slf4j
public class ClusterController {
    @Resource
    private ClusterService clusterService;
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/api/cluster/save")
    public RedissonxResponse save(@RequestBody ClusterVO cluster) {
        return clusterService.save(cluster, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/cluster/delete/{clusterName}")
    public RedissonxResponse delete(@PathVariable String clusterName) {
        return clusterService.delete(clusterName, userInfoService.fetchLoginUser().getUserId());
    }

    @PostMapping("/api/cluster/loadByName/{clusterName}")
    public RedissonxResponse loadByName(@PathVariable String clusterName) {
        return clusterService.load(clusterName);
    }

    @PostMapping("/api/cluster/query")
    public RedissonxResponse query(@RequestBody ClusterQueryReq request) {
        ClusterQueryRequest queryRequest = new ClusterQueryRequest();
        queryRequest.setApplication(request.getApplication());
        queryRequest.setKeyword(request.getKeyword());
        queryRequest.setMode(request.getMode());
        queryRequest.setTenant(request.getTenant());
        queryRequest.setUserId(request.getUserId());
        return clusterService.query(queryRequest, request.getPaging());
    }

    @GetMapping("/api/cluster/publish/{clusterName}")
    public RedissonxResponse publish(@PathVariable String clusterName) {
        return clusterService.publish(clusterName, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/cluster/offline/{clusterName}")
    public RedissonxResponse offline(@PathVariable String clusterName) {
        return clusterService.offline(clusterName, userInfoService.fetchLoginUser().getUserId());
    }
}
