package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.arch.redissonx.server.domain.connect.ConnectVO;
import com.leshiguang.arch.redissonx.server.domain.request.ConnectQueryRequest;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxTable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:14
 * @Modify
 */
public interface ConnectService {
    /**
     * 拉取集群列表
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
}
