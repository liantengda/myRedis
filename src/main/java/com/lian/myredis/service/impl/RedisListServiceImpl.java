package com.lian.myredis.service.impl;

import com.lian.myredis.common.utils.RedisUtil;
import com.lian.myredis.service.RedisListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.model.types.LSTFAbstractType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 9:06
 */
@Service
@Slf4j
public class RedisListServiceImpl implements RedisListService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List leftPushToList(String key,String value) {
        Long aLong = redisUtil.leftPushToList(key, value);
        List listContentByKey = getListContentByKey(key);
        return listContentByKey;
    }

    @Override
    public List leftPushAll(String key, String...values) {
        Long aLong = redisUtil.leftPushAllToList(key, values);
        List listContentByKey = getListContentByKey(key);
        return listContentByKey;
    }

    @Override
    public List getListContentByKey(String key) {
        List listContentByKey = redisUtil.getListContentByKey(key);
        return listContentByKey;
    }


    @Override
    public List rightPush(String key, String value) {
        Long aLong = redisUtil.rightPushToList(key, value);
        List listContentByKey = redisUtil.getListContentByKey(key);
        return listContentByKey;
    }



    @Override
    public List rightPushAll(String key, String...values) {
        Long total = redisUtil.rightPushAllToList(key, values);
        List listContentByKey = redisUtil.getListContentByKey(key);
        return listContentByKey;
    }

    @Override
    public List updateElementByIndex(String key, long index,String value) {
        redisUtil.setElementByIndex(key,index,value);
        List listContentByKey = redisUtil.getListContentByKey(key);
        return listContentByKey;
    }

    @Override
    public List removeElementByCountAndValue(String key,long count,String value) {
        Long aLong = redisUtil.removeFromList(key, count, value);
        List listContentByKey = redisUtil.getListContentByKey(key);
        return listContentByKey;
    }

    @Override
    public Long getListSize(String key) {
        Long listSize = redisUtil.getListSize(key);
        return listSize;
    }

    @Override
    public boolean delList(String key) {
        return redisUtil.deleteObject(key);
    }

    @Override
    public Object getElementByIndex(String key, long index) {
        Object elementByIndex = redisUtil.getElementByIndex(key, index);
        return elementByIndex;
    }

    @Override
    public Object leftPop(String key) {
        Object o = redisUtil.leftPopFromList(key);
        return o;
    }

    @Override
    public Object rightPop(String key) {
        Object o = redisUtil.rightPopFromList(key);
        return o;
    }
}
