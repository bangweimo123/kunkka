package com.leshiguang.arch.redissonx.config.hotkey;

import com.leshiguang.redissonx.common.entity.category.HotKeyStrategyBO;
import org.redisson.config.GuavaCacheHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 14:00
 * @Modify
 */
public class LocalCacheHotKeyStrategy implements HotKeyStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalCacheHotKeyStrategy.class);
    private Long maximumSize = 1000L;

    private Long maximumWeight = -1L;

    private String duration = "10m";

    private Integer durationInSeconds;
    /**
     * 定义的hotHolder
     */
    private GuavaCacheHolder hotHolder;

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    private int parseDurationInSeconds(String durationString) {
        try {
            if (durationString.endsWith("m")) {
                int minutes = Integer.parseInt(durationString.substring(0, durationString.length() - 1));

                return minutes * 60;
            } else if (durationString.endsWith("s")) {
                return Integer.parseInt(durationString.substring(0, durationString.length() - 1));
            } else if (durationString.endsWith("d")) {
                int days = Integer.parseInt(durationString.substring(0, durationString.length() - 1));

                return days * 24 * 3600;
            } else {
                String hourString = durationString;

                if (hourString.endsWith("h")) {
                    hourString = hourString.substring(0, hourString.length() - 1);
                }
                int hours = Integer.parseInt(hourString);
                return hours * 60 * 60;
            }
        } catch (Exception e) {
            LOGGER.error("failed to parse duration seconds, use default duration 2h!", e);
            return 2 * 60 * 60;
        }
    }

    public GuavaCacheHolder getHotHolder() {
        return hotHolder;
    }

    public Long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Long getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(Long maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public HotKeyStrategy parse(HotKeyStrategyBO strategyBO) {
        LocalCacheHotKeyStrategy localCacheHotKeyStrategy = new LocalCacheHotKeyStrategy();
        Object duration = strategyBO.getStrategyParams().get("duration");
        if (null != duration) {
            localCacheHotKeyStrategy.setDuration((String) duration);
        }
        Object maximumSize = strategyBO.getStrategyParams().get("maximumSize");
        if (null != maximumSize) {
            localCacheHotKeyStrategy.setMaximumSize((Long) maximumSize);
        }
        Object maximumWeight = strategyBO.getStrategyParams().get("maximumWeight");
        if (null != maximumWeight) {
            localCacheHotKeyStrategy.setMaximumWeight((Long) maximumWeight);
        }
        return localCacheHotKeyStrategy;
    }

    @Override
    public String getName() {
        return "local";
    }

    @Override
    public void process() {
        this.durationInSeconds = parseDurationInSeconds(this.duration);
        this.hotHolder = new GuavaCacheHolder(this);
    }
}
