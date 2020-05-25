package com.lian.myredis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lian.myredis.common.utils.RedisUtil;
import com.lian.myredis.service.RedisHashService;
import com.lian.myredis.service.RedisSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.RedisSet;
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
public class RedisSetServiceImpl implements RedisSetService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Set addElementToSet(String key, Object...values) {
        Long aLong = redisUtil.addElementToSet(key, values);
        Set allElementFromSet = getAllElementsFromSet(key);
        return allElementFromSet;
    }

    @Override
    public Set removeElementFromSet(String key, Object... values) {
        Long aLong = redisUtil.removeFromSet(key, values);
        Set allElementsFromSet = getAllElementsFromSet(key);
        return allElementsFromSet;
    }

    @Override
    public JSONObject popElementRandomlyFromSet(String key) {
        JSONObject jsonObject = new JSONObject();
        Object o = redisUtil.popFromSet(key);
        Set allElementFromSet = redisUtil.getAllElementFromSet(key);

        jsonObject.put("popElement",o);
        jsonObject.put("set",allElementFromSet);
        return jsonObject;
    }

    @Override
    public JSONObject moveElementFromSetToAnotherSet(String key, Object value, String anotherKey) {
        JSONObject jsonObject = new JSONObject();
        Boolean aBoolean = redisUtil.moveElementFromSetToAnotherSet(key, value, anotherKey);
        Set sourceSet = getAllElementsFromSet(key);
        Set targetSet = getAllElementsFromSet(anotherKey);
        jsonObject.put("sourceSet",sourceSet);
        jsonObject.put("targetSet",targetSet);
        return jsonObject;
    }

    @Override
    public Long getSetSize(String key) {
        Long setSize = redisUtil.getSetSize(key);
        return setSize;
    }

    @Override
    public Set getAllElementsFromSet(String key) {
        Set allElementFromSet = redisUtil.getAllElementFromSet(key);
        return allElementFromSet;
    }

    @Override
    public Set getNeededCountElementFromSet(String key, long neededCount) {
        Set neededCountFromSet = redisUtil.getNeededCountFromSet(key, neededCount);
        return neededCountFromSet;
    }
}
