package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.request.ClusterQueryRequest;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 13:26
 * @Modify
 */
public interface ClusterService {
    /**
     * 拉取集群列表
     *
     * @param request
     * @return
     */
    RedissonxResponse<RedissonxTable<ClusterBO>> queryClusterList(ClusterQueryRequest request, RedissonxPaging paging);

    /**
     * 拉取集群详情
     *
     * @param clusterName
     * @return
     */
    RedissonxResponse<ClusterBO> loadClusterDetail(String clusterName);

    /**
     * 新增集群
     *
     * @param cluster
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> saveCluster(ClusterBO cluster, String operator);

    /**
     * 删除集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> deleteCluster(String clusterName, String operator);
}
