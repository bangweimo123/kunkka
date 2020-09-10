package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.arch.redissonx.server.domain.category.CategoryVO;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.arch.redissonx.server.domain.request.CategoryQueryRequest;
import org.redisson.RedissonxClient;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:29
 * @Modify
 */
public interface CategoryService {
    RedissonxClient getClientByClusterName(String clusterName, String region);

    /**
     * 查询category
     *
     * @param clusterName
     * @param queryRequest
     * @param paging
     * @return
     */
    RedissonxResponse<RedissonxTable<CategoryVO>> query(String clusterName, CategoryQueryRequest queryRequest, RedissonxPaging paging);

    /**
     * 保存category
     *
     * @param clusterName
     * @param category
     * @return
     */
    RedissonxResponse<Boolean> save(String clusterName, CategoryVO category, String operator);

    /**
     * 获取category详情
     *
     * @param clusterName
     * @param category
     * @return
     */
    RedissonxResponse<CategoryVO> get(String clusterName, String category);

    /**
     * 删除category
     *
     * @param clusterName
     * @param category
     * @return
     */
    RedissonxResponse<Boolean> delete(String clusterName, String category, String operator);


    /**
     * 强制删除
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> hardDelete(String clusterName, String category, String operator);

    /**
     * 恢复
     *
     * @param clusterName
     * @param operator
     * @return
     */
    RedissonxResponse<Boolean> reset(String clusterName, String category, String operator);

    /**
     * 发布
     *
     * @param clusterName
     * @param category
     * @return
     */
    RedissonxResponse<Boolean> publish(String clusterName, String category, String version, String operator);

    /**
     * 下线
     *
     * @param clusterName
     * @param category
     * @return
     */
    RedissonxResponse<Boolean> offline(String clusterName, String category, String operator);

    /**
     * 扫描,如果没有带category,则获取clusterName下所有的category,如果带了category获取category下所有的key
     *
     * @param clusterName
     * @param category
     * @param paramFormat
     * @param tenantId
     * @param operator
     * @return
     */
    RedissonxResponse<List<String>> scan(String clusterName, String region, String category, String paramFormat, Integer tenantId, String operator);
}
