package com.lian.myredis.controller;

import com.lian.myredis.globalException.pojo.response.R;
import com.lian.myredis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 9:04
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    RedisService redisService;

    @RequestMapping(value = "/String",method = RequestMethod.GET)
    public R<String> getStringKeyValue(String key){
        String stringKeyValue = redisService.getStringKeyValue(key);
        return new R<>(stringKeyValue);
    }

    @RequestMapping(value = "/String",method = RequestMethod.POST)
    public R<Map<String,String>> setStringKeyValue(String key, String value){
        Map<String, String> stringStringMap = redisService.setStringKeyValue(key, value);
        return new R<>(stringStringMap);
    }

    @RequestMapping(value = "/String",method = RequestMethod.DELETE)
    public R<Boolean> delStringKeyValue(String key){
        boolean b = redisService.delStringKeyValue(key);
        return new R<>(b);
    }

    @RequestMapping(value = "/String",method = RequestMethod.PUT)
    public R<Map<String,String>> updStringKeyValue(String key,String value,int offset){
        Map<String, String> stringStringMap = redisService.overwriteStringKyValue(key, value, offset);
        return new R<>(stringStringMap);
    }

    @RequestMapping(value = "/StringMap",method = RequestMethod.GET)
    public R<Map<String,String>> getStringMap(String keyPart){
        Map<String, String> stringMap = redisService.getStringMapByPartOfKey(keyPart);
        return new R<>(stringMap);
    }

    @RequestMapping(value = "/incrementInt",method = RequestMethod.PUT)
    public R<Map<String,Long>> getIncrementMapLong(String key,Long summand){
        Map<String, Long> stringLongMap = redisService.incrementIntValueByKey(key,summand);
        return new R<>(stringLongMap);
    }

    @RequestMapping(value = "/incrementDouble",method = RequestMethod.PUT)
    public R<Map<String,Double>> getIncrementMapDouble(String key,Double summand){
        Map<String, Double> stringDoubleMap = redisService.incrementDoubleValueByKey(key, summand);
        return new R<>(stringDoubleMap);
    }

}
