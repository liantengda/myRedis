package com.lian.myredis.controller;

import com.lian.myredis.globalException.pojo.response.R;
import com.lian.myredis.service.RedisHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 20:20
 */
@RestController
@RequestMapping(value = "/redisHash")
public class RedisHashController {

    @Autowired
    RedisHashService redisHashService;

    @RequestMapping(value = "/hash",method = RequestMethod.POST)
    public R<Map> putIntoHash(String key,String hashKey,String value){
        Map map = redisHashService.putIntoHash(key, hashKey, value);
        return new R<>(map);
    }

    @RequestMapping(value = "/hashMap",method = RequestMethod.POST)
    public R<Map> putAllIntoHash(String key,Map map){
        Map result = redisHashService.putAllIntoHash(key, map);
        return new R<>(result);
    }

    @RequestMapping(value = "/hashAll",method = RequestMethod.GET)
    public R<Map> getAllKeyValueByKey(String key){
        Map map = redisHashService.getAllKeyValueByKey(key);
        return new R<>(map);
    }

    @RequestMapping(value = "/hashNeed",method = RequestMethod.GET)
    public R<Map> getNeedCountHashKeyValue(String key,Long needCount){
        Map map = redisHashService.getNeedCountHashKeyValue(key,needCount);
        return new R<>(map);
    }

    @RequestMapping(value = "/hashValues",method = RequestMethod.GET)
    public R<List> getAllValuesByKey(String key){
        List allValuesByKey = redisHashService.getAllValuesByKey(key);
        return new R<>(allValuesByKey);
    }

    @RequestMapping(value = "/hashSize",method = RequestMethod.GET)
    public R<Long> getHashElementSize(String key){
        Long allValuesByKey = redisHashService.getHashElementSize(key);
        return new R<>(allValuesByKey);
    }

    @RequestMapping(value = "/hashValue",method = RequestMethod.GET)
    public R<Object> getValueByKeyAndHashKey(String key,String hashKey){
        Object allValuesByKey = redisHashService.getValueByKeyAndHashKey(key,hashKey);
        return new R<>(allValuesByKey);
    }

    @RequestMapping(value = "/hashSize",method = RequestMethod.GET)
    public R<Set> getHashKeys(String key){
        Set allValuesByKey = redisHashService.getHashKeys(key);
        return new R<>(allValuesByKey);
    }

    @RequestMapping(value = "/hashKeys",method = RequestMethod.DELETE)
    public R<Long> deleteKeys(String key,String[] hashKeys){
        Long allValuesByKey = redisHashService.deleteKeys(key,hashKeys);
        return new R<>(allValuesByKey);
    }

    @RequestMapping(value = "/hasHashKey",method = RequestMethod.GET)
    public R<Boolean> hasHashKey(String key,String hashKey){
        Boolean allValuesByKey = redisHashService.hasHashKey(key,hashKey);
        return new R<>(allValuesByKey);
    }
}
