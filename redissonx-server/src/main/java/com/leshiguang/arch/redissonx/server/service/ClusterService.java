package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterVO;
import com.leshiguang.arch.redissonx.server.domain.request.ClusterQueryRequest;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxTable;

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
    RedissonxResponse<RedissonxTable<ClusterVO>> query(ClusterQueryRequest request, RedissonxPaging paging);

    /**
     * 拉取集群详情
     *
     * @param clusterName
     * @return
     */
    RedissonxResponse<ClusterVO> load(String clusterName);

    /**
     * 保存集群
     *
     * @param cluster
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> save(ClusterVO cluster, String operator);

    /**
     * 删除集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> delete(String clusterName, String operator);

    /**
     * 发布集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> publish(String clusterName, String operator);

    /**
     * 下线集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> offline(String clusterName, String operator);
}
