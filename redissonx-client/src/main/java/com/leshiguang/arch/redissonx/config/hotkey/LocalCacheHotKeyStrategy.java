package com.leshiguang.arch.redissonx.config.hotkey;

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
    private Integer maximumSize = 1000;

    private Integer maximumWeight = 1000;

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

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Integer getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(Integer maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String getName() {
        return "local";
    }

    @Override
    public void process() {
        this.hotHolder = new GuavaCacheHolder(this);
        this.durationInSeconds = parseDurationInSeconds(this.duration);
    }
}
