package com.lian.myredis.service.impl;

import com.lian.myredis.common.utils.RedisUtil;
import com.lian.myredis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 9:06
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String getStringKeyValue(String key) {
        return  redisUtil.getString(key);
    }

    @Override
    public Map<String, String> setStringKeyValue(String key, String value) {
        HashMap<String, String> stringKeyValue = new HashMap<>();
        redisUtil.setString(key,value,30,TimeUnit.MINUTES);
        String result = redisUtil.getString(key);
        stringKeyValue.put(key,result);
        return stringKeyValue;
    }

    @Override
    public boolean delStringKeyValue(String key) {
        return redisUtil.delString(key);
    }

    @Override
    public Map<String, String> overwriteStringKyValue(String key, String value, int offset) {
        HashMap<String, String> stringKeyValue = new HashMap<>();
        redisUtil.overwriteString(key,value,offset);
        String string = redisUtil.getString(key);
        stringKeyValue.put(key,string);
        return stringKeyValue;
    }

    @Override
    public Map<String,String> getStringMapByPartOfKey(String partKey) {
        Map<String, String> stringMap = redisUtil.getStringListByPartOfKey(partKey);
        return stringMap;
    }

    @Override
    public Map<String, Long> incrementIntValueByKey(String key,long summand) {
        HashMap<String, Long> incrementMap = new HashMap<>();
        Long total = redisUtil.incrementIntStringByKey(key, summand);
        incrementMap.put(key,total);
        return incrementMap;
    }

    @Override
    public Map<String, Double> incrementDoubleValueByKey(String key, Double summand) {
        HashMap<String, Double> incrementMap = new HashMap<>();
        Double aDouble = redisUtil.incrementDoubleStringByKey(key, summand);
        incrementMap.put(key,aDouble);
        return incrementMap;
    }
}
