package com.leshiguang.arch.kunkka.web.service;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryKVReq;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryKVSaveReq;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryScanReq;
import com.leshiguang.arch.kunkka.web.domain.rediskey.RedisKeyValueVO;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 12:44
 * @Modify
 */
public interface RedisKeyService {


    /**
     * 扫描,如果没有带category,则获取clusterName下所有的category,如果带了category获取category下所有的key
     */
    List<String> scan(CategoryScanReq scanReq) throws KunkkaException;

    RedisKeyValueVO kvGet(CategoryKVReq kvReq) throws KunkkaException;

    Boolean kvSave(CategoryKVSaveReq kvSaveReq) throws KunkkaException;

    Boolean kvDelete(CategoryKVReq kvReq) throws KunkkaException;
}
