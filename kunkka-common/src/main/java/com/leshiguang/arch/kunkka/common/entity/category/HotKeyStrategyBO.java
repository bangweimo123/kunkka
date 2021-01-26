package com.leshiguang.arch.kunkka.common.entity.category;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 13:55
 * @Modify
 */
@Data
public class HotKeyStrategyBO implements Serializable {
    private static final long serialVersionUID = -4645030002911054908L;
    private String strategy;

    private Map<String, Object> strategyParams = new HashMap<>();
}
