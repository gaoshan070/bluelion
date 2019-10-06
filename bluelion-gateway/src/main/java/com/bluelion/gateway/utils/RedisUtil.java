package com.bluelion.gateway.utils;

import com.bluelion.shared.utils.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String EMPTY_VALUE = "null";

    @Autowired
    private RedisTemplate redisTemplate;

    private ValueOperations<String, String> stringOperations;

    private HashOperations hashOperations;

    private ListOperations listOperations;

    @PostConstruct
    public void init() {
        stringOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
    }

    /**
     * 缓存存入空值
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void setEmpty(String key, long time, TimeUnit timeUnit) {
        logger.debug("返回值为null，缓存空值,key={},time={},timeUnit={}", key, time, timeUnit);
        stringOperations.set(key, "", time, timeUnit);
        logger.debug("空值写入成功,key={}", key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void setString(String key, String value, long time, TimeUnit timeUnit) {
        logger.debug("写入缓存,key={},value={},time={},timeUnit={}", key, value, time, timeUnit);
        stringOperations.set(key, value, time, timeUnit);
        logger.debug("缓存写入成功,key={}", key);
    }

    /**
     * 获取缓存值
     *
     * @return
     */
    public String getValue(String key) {
        String value = stringOperations.get(key);
        return value;
    }

    /**
     * 存入hash
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void writeHash(String key, T value) {
        Map<String, ?> mappedHash = null;
        try {
            mappedHash = ObjectUtil.convertBean(value);
        } catch (Exception e) {
        }
        hashOperations.putAll(key, mappedHash);
    }

    /**
     * 缓存hash空值
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void writeHashEmpty(String key, long time, TimeUnit timeUnit) {
        logger.debug("返回值为null，缓存空值,key={},time={},timeUnit={}", key, time, timeUnit);
        hashOperations.put(key, EMPTY_VALUE, EMPTY_VALUE);
        this.setHashExpireTime(key, time, timeUnit);
        logger.debug("空值写入成功,key={}", key);
    }

    /**
     * 读取hash
     *
     * @param key
     * @param beanClass
     * @param <T>
     * @return
     */
    public <T> T loadHash(String key, Class<T> beanClass) {
        Map<String, ?> loadedHash = hashOperations.entries(key);
        if (loadedHash.isEmpty() || (loadedHash.containsKey(EMPTY_VALUE) && loadedHash.containsValue(EMPTY_VALUE))) {
            return null;
        }
        Object obj = null;
        try {
            obj = ObjectUtil.convertMap(beanClass, loadedHash);
        } catch (Exception e) {

        }
        return (T) obj;
    }

    /**
     * 设置hash类型key的过期时间
     *
     * @param key
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public Boolean setHashExpireTime(String key, long timeOut, TimeUnit timeUnit) {
        Boolean result = redisTemplate.boundHashOps(key).expire(timeOut, timeUnit);
        return result;
    }

    /**
     * 写入list
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Long writeList(String key, List<T> value) {
        if (!CollectionUtils.isEmpty(value)) {
            return listOperations.leftPushAll(key, value);
        }
        return 0L;
    }

    /**
     * 获取List
     *
     * @param key
     * @param start
     * @param end
     */
    public List loadList(String key, long start, long end) {
        List list = listOperations.range(key, start, end);
        return list;
    }

    /**
     * 获取所有列表
     *
     * @param key
     * @return
     */
    public List loadListAll(String key) {
        Long size = listOperations.size(key);
        List list = listOperations.range(key, 0, size);
        return list;
    }

    /**
     * 设置list过期时间
     *
     * @param key
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public Boolean setListExpireTime(String key, int timeOut, TimeUnit timeUnit) {
        Boolean result = redisTemplate.boundListOps(key).expire(timeOut, timeUnit);
        return result;
    }
}
