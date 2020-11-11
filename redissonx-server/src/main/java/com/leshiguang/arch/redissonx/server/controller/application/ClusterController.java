package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterPublishOption;
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

    @PostMapping("/api/cluster/connects/save")
    public RedissonxResponse saveConnects(@RequestBody ClusterVO cluster) {
        RedissonxResponse<Boolean> saveResult = clusterService.saveConnects(cluster, userInfoService.fetchLoginUser().getUserId());
        return saveResult;
    }

    @GetMapping("/api/cluster/connects/publish/{clusterName}")
    public RedissonxResponse publishConnects(@PathVariable String clusterName) {
        RedissonxResponse<Boolean> isOnlineResult = clusterService.isOnline(clusterName);
        if (isOnlineResult.getData()) {
            ClusterPublishOption publishOption = new ClusterPublishOption();
            publishOption.setAll(false);
            publishOption.setAuth(false);
            publishOption.setConnect(true);
            return clusterService.publish(clusterName, userInfoService.fetchLoginUser().getUserId(), publishOption);
        } else {
            return isOnlineResult;
        }
    }


    @PostMapping("/api/cluster/auths/save")
    public RedissonxResponse saveAuths(@RequestBody ClusterVO cluster) {
        return clusterService.saveAuths(cluster, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/cluster/auths/publish/{clusterName}")
    public RedissonxResponse publishAuths(@PathVariable String clusterName) {
        RedissonxResponse<Boolean> isOnlineResult = clusterService.isOnline(clusterName);
        if (isOnlineResult.getData()) {
            ClusterPublishOption publishOption = new ClusterPublishOption();
            publishOption.setAll(false);
            publishOption.setAuth(true);
            publishOption.setConnect(false);
            return clusterService.publish(clusterName, userInfoService.fetchLoginUser().getUserId(), publishOption);
        } else {
            return isOnlineResult;
        }
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
        queryRequest.setApplicationList(request.getApplicationList());
        queryRequest.setKeyword(request.getKeyword());
        queryRequest.setClusterMode(request.getMode());
        queryRequest.setTenantList(request.getTenantList());
        queryRequest.setUserId(request.getUserId());
        queryRequest.setStatusList(request.getStatusList());
        return clusterService.query(queryRequest, request.getPaging());
    }

    @GetMapping("/api/cluster/loadConnectsByCluster/{clusterName}")
    public RedissonxResponse loadConnectsByCluster(@PathVariable String clusterName) {
        return clusterService.loadConnectsByCluster(clusterName);
    }

    @GetMapping("/api/cluster/publish/{clusterName}")
    public RedissonxResponse publish(@PathVariable String clusterName) {
        ClusterPublishOption publishOption = new ClusterPublishOption();
        publishOption.setAll(true);
        publishOption.setAuth(false);
        publishOption.setConnect(false);
        return clusterService.publish(clusterName, userInfoService.fetchLoginUser().getUserId(), publishOption);
    }

    @GetMapping("/api/cluster/offline/{clusterName}")
    public RedissonxResponse offline(@PathVariable String clusterName) {
        return clusterService.offline(clusterName, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/cluster/hardDelete/{clusterName}")
    public RedissonxResponse hardDelete(@PathVariable String clusterName) {
        return clusterService.hardDelete(clusterName, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/cluster/reset/{clusterName}")
    public RedissonxResponse reset(@PathVariable String clusterName) {
        return clusterService.reset(clusterName, userInfoService.fetchLoginUser().getUserId());
    }
}
