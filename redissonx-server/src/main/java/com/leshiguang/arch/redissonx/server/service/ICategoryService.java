package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.redissonx.common.dto.StoreCategoryConfigDTO;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:29
 * @Modify
 */
public interface ICategoryService {
    boolean create(StoreCategoryConfigDTO configDTO);

    boolean delete(String category);

    boolean update(StoreCategoryConfigDTO configDTO);
}
