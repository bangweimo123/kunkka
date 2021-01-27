package org.springframework.data.redis.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 14:44
 * @Description
 */
@Getter
@Setter
public class KunkkaScanResult implements Serializable {
    private long position;

    private List<String> data;

    public KunkkaScanResult(long position, List<String> data) {
        this.position = position;
        this.data = data;
    }
}
