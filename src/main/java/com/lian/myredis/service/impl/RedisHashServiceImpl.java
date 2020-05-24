package com.lian.myredis.service.impl;

import com.lian.myredis.common.utils.RedisUtil;
import com.lian.myredis.service.RedisHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 20:23
 */
@Service
public class RedisHashServiceImpl implements RedisHashService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Map putIntoHash(String key, String hashKey, String value) {
        redisUtil.putIntoHash(key,hashKey,value);
        Map allKeyValueByKey = getAllKeyValueByKey(key);
        return allKeyValueByKey;
    }

    @Override
    public List getAllValuesByKey(String key) {
        List allValuesByKey = redisUtil.getAllValuesByKey(key);
        return allValuesByKey;
    }

    @Override
    public Map getAllKeyValueByKey(String key) {
        Map allKeyValueByKey = redisUtil.getAllKeyValueByKey(key);
        return allKeyValueByKey;
    }

    @Override
    public Map getNeedCountHashKeyValue(String key,long needCount) {
        Map map = redisUtil.iteratorHash(key, needCount);
        return map;
    }

    @Override
    public Map putAllIntoHash(String key, Map<String, String> map) {
        redisUtil.putAllIntoHash(key,map);
        Map allKeyValueByKey = redisUtil.getAllKeyValueByKey(key);
        return allKeyValueByKey;
    }

    @Override
    public Long getHashElementSize(String key) {
        Long hashElementSize = redisUtil.getHashElementSize(key);
        return hashElementSize;
    }

    @Override
    public Set getHashKeys(String key) {
        Set hashKeys = redisUtil.getHashKeys(key);
        return hashKeys;
    }

    @Override
    public Object getValueByKeyAndHashKey(String key, String hashKey) {
        Object valueByKeyAndHashKey = redisUtil.getValueByKeyAndHashKey(key, hashKey);
        return valueByKeyAndHashKey;
    }

    @Override
    public Boolean hasHashKey(String key, String hashKey) {
        Boolean aBoolean = redisUtil.hasHashKey(key, hashKey);
        return aBoolean;
    }

    @Override
    public Long deleteKeys(String key, String... hashKeys) {
        Long delete = redisUtil.deleteKeys(key, hashKeys);
        return delete;
    }
}
