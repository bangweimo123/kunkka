package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.redissonx.common.dto.StoreCategoryConfigDTO;
import com.leshiguang.redissonx.common.path.PathProvider;

import javax.annotation.PostConstruct;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:30
 * @Modify
 */
public class CategoryServiceImpl implements ICategoryService {
    PathProvider pathProvider = new PathProvider();

    @PostConstruct
    public void init() {
        pathProvider = new PathProvider();
        pathProvider.addTemplate("category", "/redissonx/$0/bucket/@bucket/$1");
    }

    @Override
    public boolean create(StoreCategoryConfigDTO configDTO) {
        Integer bucket = configDTO.getCategory().hashCode() % 50 + 50;
        String path = pathProvider.getPathWithBucket("category", bucket, configDTO.getClusterName(), configDTO.getCategory());
        return false;
    }

    @Override
    public boolean delete(String category) {
        return false;
    }

    @Override
    public boolean update(StoreCategoryConfigDTO configDTO) {
        return false;
    }
}
