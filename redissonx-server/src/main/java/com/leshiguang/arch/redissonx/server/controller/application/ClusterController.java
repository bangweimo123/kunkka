package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterQueryReq;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterVO;
import com.leshiguang.arch.redissonx.server.service.ClusterService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.request.ClusterQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
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

    private BeanCopier beanCopier = BeanCopier.create(ClusterVO.class, ClusterBO.class, false);

    @PostMapping("/api/cluster/save")
    public RedissonxResponse save(@RequestBody ClusterVO cluster) {
        if (null != cluster) {
            cluster.convert();
        }
        ClusterBO clusterBO = new ClusterBO();
        beanCopier.copy(cluster, clusterBO, null);
        return clusterService.saveCluster(clusterBO, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/cluster/delete/{clusterName}")
    public RedissonxResponse delete(@PathVariable String clusterName) {
        return clusterService.deleteCluster(clusterName, userInfoService.fetchLoginUser().getUserId());
    }

    @PostMapping("/api/cluster/loadByName/{clusterName}")
    public RedissonxResponse loadByName(@PathVariable String clusterName) {
        return clusterService.loadClusterDetail(clusterName);
    }

    @PostMapping("/api/cluster/query")
    public RedissonxResponse query(@RequestBody ClusterQueryReq request) {
        ClusterQueryRequest queryRequest = new ClusterQueryRequest();
        return clusterService.queryClusterList(queryRequest, request.getPaging());
    }
}
