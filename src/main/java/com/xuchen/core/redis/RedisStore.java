package com.xuchen.core.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisStore {
    private static RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static Set getKeys(String key) {
        return redisTemplate.keys(key);
    }

    public static void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        expire(key, 1, TimeUnit.DAYS);
    }

    public static void setValue(String key, Object value, long timeout, TimeUnit timeType) {
        redisTemplate.opsForValue().set(key, value);
        expire(key, timeout, timeType);
    }

    public static <T> T getValue(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public static void delValue(String key) {
        redisTemplate.delete(key);
    }

    public static long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public static void delAllKey(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }

    public static void expire(String key, long timeout, TimeUnit timeType) {
        redisTemplate.expire(key, timeout, timeType);
    }
}
