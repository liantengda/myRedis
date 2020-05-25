package com.lian.myredis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lian.myredis.common.utils.RedisUtil;
import com.lian.myredis.service.RedisZSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 20:23
 */
@Service
public class RedisZSetServiceImpl implements RedisZSetService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Set addElementToZSet(String key, Object value, double score) {
        Boolean aBoolean = redisUtil.addElementToZSet(key, value, score);
        Set allElementsFromZSet = getAllElementsFromZSet(key);
        return allElementsFromZSet;
    }

    @Override
    public Set removeElementFromZSet(String key, Object... values) {
        Long aLong = redisUtil.removeFromZSet(key, values);
        Set allElementsFromZSet = getAllElementsFromZSet(key);
        return allElementsFromZSet;
    }

    @Override
    public Set addElementSetToZSet(String key, Map<String,Double> values) {
        HashSet<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        Set<String> strings = values.keySet();
        for (String hashKey:strings) {
            ZSetOperations.TypedTuple<Object> tuple = new DefaultTypedTuple<>(hashKey,values.get(hashKey));
            tuples.add(tuple);
        }
        Long aLong = redisUtil.addElementSetToZSet(key, tuples);
        Set allElementsFromSet = getAllElementsFromZSet(key);
        return allElementsFromSet;
    }


    @Override
    public JSONObject getElementRank(String key,Object value) {
        JSONObject jsonObject = new JSONObject();
        Long rankZSet = redisUtil.getRankZSet(key,value);
        jsonObject.put("rank",rankZSet);
        Set allElementsFromZSet = getAllElementsFromZSet(key);
        jsonObject.put("zSet",allElementsFromZSet);
        return jsonObject;
    }

    @Override
    public Set getElementInRangeOfIndex(String key, long start, long end) {
        Set set = redisUtil.getElementsFromZSetInRangeOfIndex(key, start, end);
        return set;
    }

    @Override
    public Set getElementInRangeOfScore(String key, double min, double max) {
        Set elementFromZSetInRangeOfScore = redisUtil.getElementFromZSetInRangeOfScore(key, min, max);
        return elementFromZSetInRangeOfScore;
    }

    @Override
    public Long getZSetSize(String key) {
        Long zSetSize = redisUtil.getSetSize(key);
        return zSetSize;
    }

    @Override
    public Set getAllElementsFromZSet(String key) {
        Set allElementsFromZSet = redisUtil.getAllElementsFromZSet(key);
        return allElementsFromZSet;
    }

    @Override
    public List getNeededCountElementFromZSet(String key, long neededCount) {
        List neededCountFromSet = redisUtil.getNeededCountFromZSet(key, neededCount);
        return neededCountFromSet;
    }
}
