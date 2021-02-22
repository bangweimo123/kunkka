package com.leshiguang.arch.kunkka.web.service;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterAuthVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterConnectVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterQueryReq;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Author bangwei.mo
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
    Page<ClusterVO> query(ClusterQueryReq request, PageRequest page) throws KunkkaException;

    /**
     * 拉取集群详情
     *
     * @param clusterName
     * @return
     */
    ClusterVO load(String clusterName) throws KunkkaException;

    /**
     * 保存集群
     *
     * @param cluster
     * @param operator
     * @return
     */
    Boolean save(ClusterVO cluster, String operator) throws KunkkaException;

    /**
     * @param cluster
     * @param operator
     * @return
     * @throws KunkkaException
     */
    Boolean onlineSave(ClusterVO cluster, String operator) throws KunkkaException;

    /**
     * 保存集群连接
     *
     * @param clusterConnect
     * @param operator
     * @return
     */
    Boolean saveConnect(ClusterConnectVO clusterConnect, String operator) throws KunkkaException;

    /**
     * 通过集群获取连接,返回区域对应的连接对象
     *
     * @param clusterName
     * @return
     */
    ClusterConnectVO loadConnect(String clusterName, String region) throws KunkkaException;

    /**
     * 保存集群权限
     *
     * @param operator
     * @return
     * @throws KunkkaException
     */
    Boolean saveAuth(ClusterAuthVO clusterAuth, String operator) throws KunkkaException;

    /**
     * 加载权限信息
     *
     * @param clusterName
     * @return
     * @throws KunkkaException
     */
    List<ClusterAuthVO> loadAuths(String clusterName) throws KunkkaException;

    /**
     * @return
     * @throws KunkkaException
     */
    Boolean deleteAuth(Integer authId) throws KunkkaException;

    /**
     * 删除集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    Boolean delete(String clusterName, String operator) throws KunkkaException;

    /**
     * 强制删除
     *
     * @param clusterName
     * @param operator
     * @return
     */
    Boolean hardDelete(String clusterName, String operator) throws KunkkaException;

    /**
     * 恢复
     *
     * @param clusterName
     * @param operator
     * @return
     */
    Boolean reset(String clusterName, String operator) throws KunkkaException;

    /**
     * 发布集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    Boolean publish(String clusterName, String operator,Boolean isRePublish) throws KunkkaException;

    /**
     * 下线集群
     *
     * @param clusterName
     * @param operator
     * @return
     */
    Boolean offline(String clusterName, String operator) throws KunkkaException;

    /**
     * 获取区域列表
     *
     * @param clusterName
     * @return
     * @throws KunkkaException
     */
    List<String> loadRegions(String clusterName) throws KunkkaException;

    /**
     * 是否有权限
     *
     * @param clusterName
     * @param operator
     * @return
     * @throws KunkkaException
     */
    Boolean hasPrivilege(String clusterName, String operator) throws KunkkaException;
}
