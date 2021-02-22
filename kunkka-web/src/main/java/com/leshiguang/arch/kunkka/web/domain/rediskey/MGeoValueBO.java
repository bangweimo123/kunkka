package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2021-02-04 11:00
 * @Description
 */
@Getter
@Setter
public class MGeoValueBO implements AbstractValueBO {
    private List<MPointValueBO> values;

    @Override
    public <T> T parse() {
        List<RedisGeoCommands.GeoLocation> locationList = new ArrayList<>();
        for (MPointValueBO value : values) {
            Point point = new Point(value.getX(), value.getY());
            Object data = value.parse();
            RedisGeoCommands.GeoLocation geoLocation = new RedisGeoCommands.GeoLocation(data, point);
            locationList.add(geoLocation);
        }
        return (T) locationList;
    }
}
