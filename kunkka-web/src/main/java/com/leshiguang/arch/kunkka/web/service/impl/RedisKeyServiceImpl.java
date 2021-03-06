package com.leshiguang.arch.kunkka.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.TenantStoreKey;
import com.leshiguang.arch.kunkka.common.enums.RedisKeyType;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.web.client.adapter.KunkkaClientHolder;
import com.leshiguang.arch.kunkka.web.domain.base.KunkkaPaging;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryKVReq;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryKVSaveReq;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryScanReq;
import com.leshiguang.arch.kunkka.web.domain.rediskey.*;
import com.leshiguang.arch.kunkka.web.exception.ServerErrorCode;
import com.leshiguang.arch.kunkka.web.service.CategoryService;
import com.leshiguang.arch.kunkka.web.service.RedisKeyService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author bangwei.mo
 * @Date 2020-03-25 12:45
 * @Modify
 */
@Service
public class RedisKeyServiceImpl implements RedisKeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisKeyServiceImpl.class);
    @Resource
    private CategoryService categoryService;
    @Resource
    private KunkkaClientHolder kunkkaClientHolder;

    @Override
    public Collection<String> scan(CategoryScanReq scanReq) throws KunkkaException {
        Collection<String> result = null;
        if (StringUtils.isBlank(scanReq.getCategory())) {
            result = categoryService.loadOnlineCategorys(scanReq.getClusterName());
        } else {
            KunkkaClient kunkkaClient = kunkkaClientHolder.getClientByClusterName(scanReq.getClusterName(), scanReq.getRegion());
            if (null == kunkkaClient) {
                throw new KunkkaException(ServerErrorCode.KUNKKA_CLIENT_NOT_FOUND_ERROR);
            }
            StringBuffer pattern = new StringBuffer();
            pattern.append(scanReq.getCategory());
            if (scanReq.getScanEabled()) {
                pattern.append("\\.");
                if (StringUtils.isNotBlank(scanReq.getParamFormat())) {
                    pattern.append(scanReq.getParamFormat());
                }
                pattern.append("*");
            } else {
                pattern.append(".");
                if (StringUtils.isNotBlank(scanReq.getParamFormat())) {
                    pattern.append(scanReq.getParamFormat());
                }
            }
            if (null != scanReq.getTenantId() && scanReq.getTenantId() > 0) {
                pattern.append("@t");
                pattern.append(scanReq.getTenantId());
            }
            if (null == scanReq.getPageSize()) {
                scanReq.setPageSize(5000l);
            }
            if (scanReq.getScanEabled()) {
                result = kunkkaClient.scan(pattern.toString(), scanReq.getPageSize());
            } else {
                if (kunkkaClient.hasKey(pattern.toString())) {
                    result = Arrays.asList(pattern.toString());
                } else {
                    throw new KunkkaException(ServerErrorCode.NOT_EXIST_KEY_FOR_SCAN);
                }
            }
        }
        return result;
    }

    @Override
    public RedisKeyValueVO kvGet(CategoryKVReq kvReq) {
        KunkkaClient kunkkaClient = kunkkaClientHolder.getClientByClusterName(kvReq.getClusterName(), kvReq.getRegion());
        if (null == kunkkaClient) {
            throw new KunkkaException(ServerErrorCode.KUNKKA_CLIENT_NOT_FOUND_ERROR);
        }
        if (StringUtils.isBlank(kvReq.getKey())) {
            return null;
        }
        RedisKeyValueVO valueVO = new RedisKeyValueVO();
        String type = kvReq.getType();
        if (StringUtils.isBlank(kvReq.getType())) {
            type = "string";
        }
        RedisKeyType redisKeyType = RedisKeyType.parse(type);
        if (null == redisKeyType) {
            throw new KunkkaException(ServerErrorCode.REDIS_KEY_TYPE_NOT_SUPPORT);
        }
        long remainTimeToLive = -1l;
        try {
            switch (redisKeyType) {
                case string:
                    BoundValueOperations boundValueOperations = kunkkaClient.boundValueOps(kvReq.getKey());
                    remainTimeToLive = boundValueOperations.getExpire();
                    Object mString = boundValueOperations.get();
                    valueVO.setData(mString);
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case list:
                    BoundListOperations boundListOperations = kunkkaClient.boundListOps(kvReq.getKey());
                    remainTimeToLive = boundListOperations.getExpire();
                    Long size = boundListOperations.size();
                    if (size > 1) {
                        List<Object> mList = boundListOperations.range(0, size - 1);
                        valueVO.setData(JSON.toJSONString(mList));
                    }
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case set:
                    BoundSetOperations boundSetOperations = kunkkaClient.boundSetOps(kvReq.getKey());
                    remainTimeToLive = boundSetOperations.getExpire();
                    Set<Object> mSet = boundSetOperations.members();
                    valueVO.setData(JSON.toJSONString(mSet));
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case hash:
                    BoundHashOperations boundHashOperations = kunkkaClient.boundHashOps(kvReq.getKey());
                    Set keys = boundHashOperations.keys();
                    Map mHash = new HashMap();
                    if (CollectionUtils.isNotEmpty(keys)) {
                        for (Object key : keys) {
                            mHash.put(key, boundHashOperations.get(key));
                        }
                    }
                    valueVO.setData(JSON.toJSONString(mHash));
                    valueVO.setRemainTimeToLive(boundHashOperations.getExpire());
                    break;
                case zset:
                case geo:
                    BoundZSetOperations boundZSetOperations = kunkkaClient.boundZSetOps(kvReq.getKey());
                    if (null == kvReq.getPaging()) {
                        KunkkaPaging paging = new KunkkaPaging();
                        paging.setPageIndex(1);
                        paging.setPageSize(20);
                        kvReq.setPaging(paging);
                    }
                    Integer pageSize = kvReq.getPaging().getPageSize();
                    Integer pageIndex = kvReq.getPaging().getPageIndex();
                    Set<ZSetOperations.TypedTuple> mZSet = boundZSetOperations.rangeWithScores((pageIndex - 1) * pageSize, pageIndex * pageSize);
                    valueVO.setData(JSON.toJSONString(mZSet));
                    valueVO.setRemainTimeToLive(boundZSetOperations.getExpire());
                    break;
                case bitmap:
                    BoundBitMapOperations boundBitMapOperations = kunkkaClient.boundBitMapOps(kvReq.getKey());
                    if (null == kvReq.getPaging()) {
                        KunkkaPaging paging = new KunkkaPaging();
                        paging.setPageIndex(1);
                        paging.setPageSize(10);
                        kvReq.setPaging(paging);
                    }
                    Map<Long, Boolean> mBitMap = boundBitMapOperations.getBits(Long.valueOf((kvReq.getPaging().getPageIndex() - 1) * kvReq.getPaging().getPageSize()), Long.valueOf(kvReq.getPaging().getPageIndex() * kvReq.getPaging().getPageSize()));
                    boundBitMapOperations.bitCount();
                    JSONArray json = new JSONArray();
                    for (Map.Entry<Long, Boolean> d : mBitMap.entrySet()) {
                        JSONObject obj = new JSONObject();
                        obj.put("offset", d.getKey());
                        obj.put("value", d.getValue());
                        json.add(obj);
                    }
                    valueVO.setData(json);
                    valueVO.setRemainTimeToLive(boundBitMapOperations.getExpire());
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("value parse error", e);
            throw new KunkkaException(ServerErrorCode.REDIS_KEY_VALUE_GET_ERROR, e);
        }
        return valueVO;
    }

    @Override
    public Boolean kvSave(CategoryKVSaveReq kvSaveReq) {
        KunkkaClient kunkkaClient = kunkkaClientHolder.getClientByClusterName(kvSaveReq.getClusterName(), kvSaveReq.getRegion());
        if (null == kunkkaClient) {
            throw new KunkkaException(ServerErrorCode.KUNKKA_CLIENT_NOT_FOUND_ERROR);
        }
        StoreKey storeKey;
        if (kvSaveReq.getTenantId() != null && kvSaveReq.getTenantId() > 0) {
            storeKey = new TenantStoreKey(kvSaveReq.getCategory(), kvSaveReq.getTenantId(), kvSaveReq.getParams());
        } else {
            storeKey = new StoreKey(kvSaveReq.getCategory(), kvSaveReq.getParams());
        }
        JSONObject data = kvSaveReq.getData();
        RedisKeyType redisKeyType = RedisKeyType.parse(kvSaveReq.getType());
        switch (redisKeyType) {
            case string:
                MValueBO mString = JSON.toJavaObject(data, MValueBO.class);
                BoundValueOperations boundValueOperations = kunkkaClient.boundValueOps(storeKey);
                boundValueOperations.set(mString.parse());
                break;
            case list:
                MListValueBO mList = JSON.toJavaObject(data, MListValueBO.class);
                BoundListOperations boundListOperations = kunkkaClient.boundListOps(storeKey);
                switch (mList.getOption()) {
                    case "lpush":
                        boundListOperations.leftPushAll(mList.parse());
                        break;
                    case "rpush":
                        boundListOperations.rightPushAll(mList.parse());
                        break;
                    default:
                        boundListOperations.rightPushAll(mList.parse());
                        break;
                }
                break;
            case set:
                MSetValueBO mSet = JSON.toJavaObject(data, MSetValueBO.class);
                BoundSetOperations boundSetOperations = kunkkaClient.boundSetOps(storeKey);
                boundSetOperations.add(mSet.parse());
                break;
            case zset:
                MZSetValueBO mZSet = JSON.toJavaObject(data, MZSetValueBO.class);
                Set<ZSetOperations.TypedTuple> typedTupleSet = mZSet.parse();
                BoundZSetOperations boundZSetOperations = kunkkaClient.boundZSetOps(storeKey);
                boundZSetOperations.add(typedTupleSet);
                break;
            case geo:
                MGeoValueBO mGeo = JSON.toJavaObject(data, MGeoValueBO.class);
                List<RedisGeoCommands.GeoLocation> locationList = mGeo.parse();
                BoundGeoOperations boundGeoOperations = kunkkaClient.boundGeoOps(storeKey);
                boundGeoOperations.add(locationList);
                break;
            case hash:
                MHashValueBO mHash = JSON.toJavaObject(data, MHashValueBO.class);
                BoundHashOperations boundHashOperations = kunkkaClient.boundHashOps(storeKey);
                Map hashMap = mHash.parse();
                boundHashOperations.putAll(hashMap);
                break;
            case bitmap:
                MBitMapValueBO mBitMap = JSON.toJavaObject(data, MBitMapValueBO.class);
                BoundBitMapOperations boundBitMapOperations = kunkkaClient.boundBitMapOps(storeKey);
                Map<Long, Boolean> bistMap = mBitMap.parse();
                boundBitMapOperations.setBits(bistMap);
                break;
        }
        return true;
    }

    @Override
    public Boolean kvDelete(CategoryKVReq kvReq) {
        KunkkaClient kunkkaClient = kunkkaClientHolder.getClientByClusterName(kvReq.getClusterName(), kvReq.getRegion());
        return kunkkaClient.delete(kvReq.getKey());
    }
}
