package com.leshiguang.arch.kunkka.web.service;

import com.leshiguang.arch.kunkka.web.domain.category.CategoryQueryReq;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryVO;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:29
 * @Modify
 */
public interface CategoryService {
    /**
     * 查询category
     *
     * @return
     */
    Page<CategoryVO> query(CategoryQueryReq queryRequest, PageRequest page) throws KunkkaException;

    /**
     * 保存category
     *
     * @return
     */
    Boolean save(CategoryVO category, String operator) throws KunkkaException;

    /**
     * 获取category详情
     *
     * @return
     */
    CategoryVO load(String clusterName, String category,String operator) throws KunkkaException;

    /**
     * 删除category
     *
     * @return
     */
    Boolean delete(Integer categoryId, String operator) throws KunkkaException;


    /**
     * 强制删除
     *
     * @return
     */
    Boolean hardDelete(Integer categoryId, String operator) throws KunkkaException;

    /**
     * 恢复
     *
     * @return
     */
    Boolean reset(Integer categoryId, String operator) throws KunkkaException;

    /**
     * 发布
     *
     * @return
     */
    Boolean publish(Integer categoryId, String operator) throws KunkkaException;

    /**
     * 下线
     *
     * @return
     */
    Boolean offline(Integer categoryId, String operator) throws KunkkaException;

    /**
     * 下载所有的category
     *
     * @param clusterName
     * @return
     * @throws KunkkaException
     */
    List<String> loadOnlineCategorys(String clusterName) throws KunkkaException;
}
