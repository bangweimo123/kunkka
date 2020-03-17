package com.leshiguang.arch.redissonx.server.controller;

import com.leshiguang.redissonx.common.dto.StoreCategoryConfigDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:17
 * @Modify
 */
@Controller
public class CategoryController {
    @RequestMapping("/create")
    public void create(String clusterName, StoreCategoryConfigDTO configDTO) {

    }
}
