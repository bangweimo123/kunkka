package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterConnectVO;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterPublishOption;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterVO;
import com.leshiguang.arch.redissonx.server.domain.connect.ConnectVO;
import com.leshiguang.arch.redissonx.server.domain.request.ClusterQueryRequest;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.cluster.ClusterConnectBO;

import java.util.List;

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
     * 保存集群连接
     *
     * @param cluster
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> saveConnects(ClusterVO cluster, String operator);

    /**
     * 保存集群权限
     *
     * @param cluster
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> saveAuths(ClusterVO cluster, String operator);

    /**
     * 删除集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> delete(String clusterName, String operator);

    /**
     * 强制删除
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> hardDelete(String clusterName, String operator);

    RedissonxResponse<Boolean> isOnline(String clusterName);

    /**
     * 恢复
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> reset(String clusterName, String operator);

    /**
     * 发布集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> publish(String clusterName, String operator, ClusterPublishOption option);

    /**
     * 下线集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> offline(String clusterName, String operator);

    /**
     * 通过集群获取连接
     *
     * @param clusterName
     * @return
     */
    RedissonxResponse<List<ClusterConnectVO>> loadConnectsByCluster(String clusterName);
}
