package com.lian.myredis.controller;

import com.lian.myredis.globalException.pojo.response.R;
import com.lian.myredis.service.RedisListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 9:04
 */
@RestController
@RequestMapping(value = "/redisList")
public class RedisListController {

    @Autowired
    RedisListService redisListService;

    @RequestMapping(value = "/leftPush",method = RequestMethod.POST)
    public R<List> getStringKeyValue(String key, String value){
        List list = redisListService.leftPushToList(key, value);
        return new R<>(list);
    }

    @RequestMapping(value = "/leftPushAll",method = RequestMethod.POST)
    public R<List> setStringKeyValue(String key, String[] values){
        List list = redisListService.leftPushAll(key, values);
        return new R<>(list);
    }

    @RequestMapping(value = "/listContent",method = RequestMethod.GET)
    public R<List> delStringKeyValue(String key){
        List listContentByKey = redisListService.getListContentByKey(key);
        return new R<>(listContentByKey);
    }

    @RequestMapping(value = "/rightPush",method = RequestMethod.POST)
    public R<List> updStringKeyValue(String key,String value){
        List list = redisListService.rightPush(key, value);
        return new R<>(list);
    }

    @RequestMapping(value = "/rightPushAll",method = RequestMethod.POST)
    public R<List> getStringMap(String key,String[] values){
        List list = redisListService.rightPushAll(key, values);
        return new R<>(list);
    }

    @RequestMapping(value = "/incrementInt",method = RequestMethod.PUT)
    public R<Map<String,Long>> getIncrementMapLong(String key,Long summand){
        return new R<>();
    }

    @RequestMapping(value = "/incrementDouble",method = RequestMethod.PUT)
    public R<Map<String,Double>> getIncrementMapDouble(String key,Double summand){
        Map<String, Double> stringDoubleMap = redisListService.incrementDoubleValueByKey(key, summand);
        return new R<>(stringDoubleMap);
    }

    @RequestMapping(value = "/appendString",method = RequestMethod.PUT)
    public R<Map<String,String>> appendStringMap(String key,String value){
        Map<String, String> stringMap = redisListService.appendStringKeyValue(key, value);
        return new R<>(stringMap);
    }

    @RequestMapping(value = "/size",method = RequestMethod.GET)
    public R<Long> getListSize(String key){
        Long stringSize = redisListService.getListSize(key);
        return new R<>(stringSize);
    }

}
