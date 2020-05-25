package com.lian.myredis.controller;

import com.alibaba.fastjson.JSONObject;
import com.lian.myredis.globalException.pojo.response.R;
import com.lian.myredis.service.RedisHashService;
import com.lian.myredis.service.RedisSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value = "/redisSet")
@Slf4j
public class RedisSetController {

    @Autowired
    RedisSetService redisSetService;

    @RequestMapping(value = "/set",method = RequestMethod.POST)
    public R<Set> putIntoHash(String key,String...values){
        Set set = redisSetService.addElementToSet(key, values);
        return new R<>(set);
    }

    @RequestMapping(value = "/set",method = RequestMethod.DELETE)
    public R<Set> removeElementFromSet(String key,String...values){
        Set set = redisSetService.removeElementFromSet(key, values);
        return new R<>(set);
    }

    @RequestMapping(value = "/set",method = RequestMethod.GET)
    public R<Set> listSet(String key){
        Set set = redisSetService.getAllElementsFromSet(key);
        return new R<>(set);
    }

    @RequestMapping(value = "/neededSet",method = RequestMethod.GET)
    public R<Set> getNeededCountElementFromSet(String key,Long neededCount){
        Set set = redisSetService.getNeededCountElementFromSet(key,neededCount);
        return new R<>(set);
    }

    @RequestMapping(value = "/popSet",method = RequestMethod.GET)
    public R<JSONObject> popSet(String key){
        JSONObject set = redisSetService.popElementRandomlyFromSet(key);
        return new R<>(set);
    }

    @RequestMapping(value = "/size",method = RequestMethod.GET)
    public R<Long> getSetSize(String key){
        Long set = redisSetService.getSetSize(key);
        return new R<>(set);
    }
}
