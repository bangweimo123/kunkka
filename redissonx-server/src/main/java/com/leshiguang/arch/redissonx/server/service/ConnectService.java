package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.arch.redissonx.server.domain.connect.ConnectVO;
import com.leshiguang.arch.redissonx.server.domain.request.ConnectQueryRequest;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxTable;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:14
 * @Modify
 */
public interface ConnectService {
    /**
     * 拉取连接列表
     *
     * @param request
     * @return
     */
    RedissonxResponse<RedissonxTable<ConnectVO>> query(ConnectQueryRequest request, RedissonxPaging paging);

    /**
     * 保存连接
     *
     * @param connect
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> save(ConnectVO connect, String operator);

    /**
     * 删除连接
     *
     * @param connectName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> delete(String connectName, String operator);

    /**
     * 加载连接对应的集群
     *
     * @param connectName
     * @return
     */
    RedissonxResponse<List<String>> loadRelationClusters(String connectName);
}
