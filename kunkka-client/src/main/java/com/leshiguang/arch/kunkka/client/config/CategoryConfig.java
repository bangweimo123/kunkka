package com.leshiguang.arch.kunkka.client.config;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.TenantStoreKey;
import com.leshiguang.arch.kunkka.client.exception.KunkkaConfigException;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2020-03-13 12:22
 * @Modify
 */
public class CategoryConfig implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryConfig.class);


    private static final ThreadLocal<StringBuilderHelper>
            threadLocalStringBuilderHelper = new ThreadLocal<StringBuilderHelper>() {
        @Override
        protected StringBuilderHelper initialValue() {
            return new StringBuilderHelper();
        }
    };
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
    private Boolean isHot = false;
    /**
     * 所使用的类型
     */
    private String cType;
    /**
     * can change duration
     */
    private Boolean durationChangeabled = false;

    private int durationInSeconds = -1;

    private Boolean multiTenant = false;

    private int hash = -1;

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIndexTemplate() {
        return indexTemplate;
    }

    public void setIndexTemplate(String indexTemplate) {
        this.indexTemplate = indexTemplate;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Boolean getMultiTenant() {
        return multiTenant;
    }

    public void setMultiTenant(Boolean multiTenant) {
        this.multiTenant = multiTenant;
    }

    public int getDurationSeconds() {
        if (durationInSeconds != -1) {
            return durationInSeconds;
        }

        synchronized (this) {
            if (durationInSeconds == -1) {
                durationInSeconds = parseDurationInSeconds(duration);
            }
        }

        return durationInSeconds;
    }

    private int parseDurationInSeconds(String durationString) {
        try {
            if (durationString.equalsIgnoreCase("-1")) {
                return -2;
            }
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

    public Boolean getDurationChangeabled() {
        return durationChangeabled;
    }

    public void setDurationChangeabled(Boolean durationChangeabled) {
        this.durationChangeabled = durationChangeabled;
    }

    private String getFinalKey1(StringBuilder buf, String tenantId, Object... params) {
        buf = buildFinalKey(buf, params);
        buf.append("@t").append(tenantId);
        return buf.toString();
    }

    private String getFinalKey0(StringBuilder buf, Object... params) {
        buf = buildFinalKey(buf, params);
        return buf.toString();
    }

    private StringBuilder buildFinalKey(StringBuilder buf, Object... params) {
        buf.append(category).append('.');

        if (indexTemplate != null) {
            char[] chars = indexTemplate.toCharArray();
            byte state = 0;
            for (char c : chars) {
                switch (state) {
                    case 0:
                        if (c != '{') {
                            buf.append(c);
                        } else {
                            state = 1;
                        }
                        break;
                    case 1:
                        int idx = c - '0';
                        if (params.length > idx) {
                            buf.append(params[idx]);
                        } else {
                            throw new KunkkaConfigException("IndexParams is not enough. Need at least " + (idx + 1) + " params.");
                        }
                        state = 2;
                        break;
                    case 2:
                        state = 0;
                }
            }
        } else {
            if (params != null && params.length > 0) {
                KunkkaException exception = new KunkkaConfigException("IndexTemplate is null. Please check your code logic and ensure category template is right.");
                LOGGER.warn("IndexTemplate is null", exception);
            }
            buf.append("null");
        }
        return buf;
    }

    /**
     * Return the string key for cache store. Key
     * Rule:{category}.{index}@{tenantId}
     */

    public String getFinalKey(StoreKey storeKey) {
        StringBuilder buf = threadLocalStringBuilderHelper.get().getStringBuilder();
        if (storeKey instanceof TenantStoreKey) {
            if (null != ((TenantStoreKey) storeKey).getTenantId()) {
                String tenant = Integer.toString(((TenantStoreKey) storeKey).getTenantId());
                return getFinalKey1(buf, tenant, storeKey.getParams());
            } else {
                return getFinalKey0(buf, storeKey.getParams());
            }
        } else {
            return getFinalKey0(buf, storeKey.getParams());
        }
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("category", category)
                .append("duration", duration)
                .append("template", indexTemplate)
                .append("isHot", isHot)
                .append("cType", cType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CategoryConfig)) {
            return false;
        }
        CategoryConfig config = (CategoryConfig) o;


        if (!getCategory().equals(config.getCategory())) {
            return false;
        }

        if (getIndexTemplate() != null ? !getIndexTemplate().equals(config.getIndexTemplate()) : config.getIndexTemplate() != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hash == -1) {
            hash = getCategory() != null ? getCategory().hashCode() : 0;
            hash = 31 * hash + (getIndexTemplate() != null ? getIndexTemplate().hashCode() : 0);
        }

        return hash;
    }

    static class StringBuilderHelper {
        final StringBuilder sb;    // Placeholder for finalKey

        StringBuilderHelper() {
            sb = new StringBuilder(128);
        }

        // Accessors.
        StringBuilder getStringBuilder() {
            sb.setLength(0);
            return sb;
        }
    }
}
