package com.leshiguang.arch.redissonx.server.service.impl;

import com.leshiguang.arch.redissonx.server.service.ClusterService;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.request.ClusterQueryRequest;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 13:27
 * @Modify
 */
@Service
public class ClusterServiceImpl implements ClusterService {
    private ZookeeperClient zookeeperClient = new ZookeeperClientImpl();

    @Override
    public RedissonxResponse<RedissonxTable<ClusterBO>> queryClusterList(ClusterQueryRequest request, RedissonxPaging paging) {
        List<ClusterBO> clusterList = zookeeperClient.loadAllCluster();
        RedissonxTable result = new RedissonxTable<ClusterBO>(paging.getPageSize(), paging.getPageIndex(), clusterList);
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<ClusterBO> loadClusterDetail(String clusterName) {
        ClusterBO clusterBO = zookeeperClient.getCluster(clusterName);
        return RedissonxResponseBuilder.success(clusterBO);
    }

    @Override
    public RedissonxResponse<Boolean> saveCluster(ClusterBO cluster, String operator) {
        RedissonxResponse result = validatorCluster(cluster);
        if (null == result) {
            zookeeperClient.setCluster(cluster);
            return RedissonxResponseBuilder.success(true);
        }
        return result;
    }

    @Override
    public RedissonxResponse<Boolean> deleteCluster(String clusterName, String operator) {
        Boolean result = zookeeperClient.deleteCluster(clusterName);
        return RedissonxResponseBuilder.success(result);
    }

    private RedissonxResponse validatorCluster(ClusterBO cluster) {
        try {
            Validate.notBlank(cluster.getClusterName(), "集群名不能为空");
        } catch (IllegalArgumentException iae) {
            return RedissonxResponseBuilder.fail(604, iae.getMessage());
        } catch (NullPointerException npe) {
            return RedissonxResponseBuilder.fail(605, "空指针异常");
        }
        return null;
    }
}
