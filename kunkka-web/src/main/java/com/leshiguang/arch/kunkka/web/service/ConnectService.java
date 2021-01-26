package com.leshiguang.arch.kunkka.web.service;

import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectQueryReq;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    Page<ConnectVO> query(ConnectQueryReq request, PageRequest page) throws KunkkaException;

    /**
     * 通过connectName获取对象
     *
     * @param connectName
     * @return
     * @throws KunkkaException
     */
    ConnectBO loadBOByName(String connectName) throws KunkkaException;

    /**
     * @param connectName
     * @return
     * @throws KunkkaException
     */
    ConnectVO loadVOByName(String connectName) throws KunkkaException;

    /**
     * 保存连接
     *
     * @param connect
     * @param operator
     * @return
     */
    Boolean save(ConnectVO connect, String operator) throws KunkkaException;

    /**
     * 删除连接
     *
     * @param connectName
     * @param operator
     * @return
     */
    Boolean delete(String connectName, String operator) throws KunkkaException;

    /**
     * 强制删除
     *
     * @param connectName
     * @param operator
     * @return
     * @throws KunkkaException
     */
    Boolean hardDelete(String connectName, String operator) throws KunkkaException;

    /**
     * 恢复
     *
     * @param connectName
     * @param operator
     * @return
     * @throws KunkkaException
     */
    Boolean reset(String connectName, String operator) throws KunkkaException;

    /**
     * @param connectName
     * @return
     * @throws KunkkaException
     */
    List<String> loadRelationClusters(String connectName) throws KunkkaException;
}
