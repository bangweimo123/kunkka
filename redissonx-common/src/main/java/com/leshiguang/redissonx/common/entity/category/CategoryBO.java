package com.leshiguang.redissonx.common.entity.category;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:26
 * @Modify
 */
@Data
public class CategoryBO implements Serializable {
    private static final long serialVersionUID = -8154502424484404775L;
    /**
     * clusterName
     */
    private String clusterName;
    /**
     * category name
     */
    private String category;
    /**
     * Duration(default hour)
     * support time unit: hour, minute, second
     * as:
     * 3(h)	3 hours
     * 4m		4 minutes
     * 5s      5 seconds
     */
    private String duration;

    /**
     * index template, such as c{0}st{1}rt{2}
     */
    private String indexTemplate;

    /**
     * is hot key,store in ehcache or guava
     */
    private boolean isHot;
    /**
     * 版本号,通过版本号来删除相应的category
     */
    private String version;
    /**
     * 序列化方式,用于数据存储的序列化
     */
    private String codec;
    /**
     * 热key对应的策略
     */
    private List<HotKeyStrategyBO> hotKeyStrategyList = new ArrayList<>();
}
