package com.lian.myredis.controller;

import com.lian.myredis.globalException.pojo.response.R;
import com.lian.myredis.service.RedisListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(value = "/element",method = RequestMethod.PUT)
    public R<List> getIncrementMapLong(String key,Long index,String value){
        List list = redisListService.updateElementByIndex(key, index, value);
        return new R<>(list);
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public R<Object> getElementByIndex(String key,Long index){
        Object elementByIndex = redisListService.getElementByIndex(key, index);
        return new R<>(elementByIndex);
    }

    @RequestMapping(value = "/remove",method = RequestMethod.DELETE)
    public R<List> removeFromList(String key,long count ,String value){
        List list = redisListService.removeElementByCountAndValue(key, count, value);
        return new R<>(list);
    }

    @RequestMapping(value = "/size",method = RequestMethod.GET)
    public R<Long> getListSize(String key){
        Long stringSize = redisListService.getListSize(key);
        return new R<>(stringSize);
    }

    @RequestMapping(value = "/leftPop",method = RequestMethod.GET)
    public R<Object> leftPop(String key){
        Object o = redisListService.leftPop(key);
        return new R<>(o);
    }

    @RequestMapping(value = "/rightPop",method = RequestMethod.GET)
    public R<Object> rightPop(String key){
        Object o = redisListService.rightPop(key);
        return new R<>(o);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public R<Boolean> delete(String key){
        boolean b = redisListService.delList(key);
        return new R<>(b);
    }

}
