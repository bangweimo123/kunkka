package com.leshiguang.arch.kunkka.common.entity.category;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:26
 * @Modify
 */
@Getter
@Setter
public class CategoryBO implements Serializable {
    private static final long serialVersionUID = -8154502424484404775L;
    /**
     * bind cluster name
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
     * store type
     */
    private String cType;
    /**
     * can change duration
     */
    private boolean durationChangeEnabled;
    /**
     * multi tenant only
     */
    private boolean multiTenant;
}
