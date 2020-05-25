package com.lian.myredis.controller;

import com.lian.myredis.globalException.pojo.response.R;
import com.lian.myredis.service.RedisZSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 20:20
 */
@RestController
@RequestMapping(value = "/redisZSet")
@Slf4j
public class RedisZSetController {

    @Autowired
    RedisZSetService redisZSetService;

    @RequestMapping(value = "/zSet",method = RequestMethod.POST)
    public R<Set> putIntoHash(String key,String value,double score){
        Set set = redisZSetService.addElementToZSet(key, value,score);
        return new R<>(set);
    }

    @RequestMapping(value = "/setToZSet",method = RequestMethod.POST)
    public R<Set> getSize(String key,@RequestBody  Map<String,Double> values){
        log.info("values{}",values);
        Set set = redisZSetService.addElementSetToZSet(key, values);
        return new R<>(set);
    }

    @RequestMapping(value = "/zSet",method = RequestMethod.DELETE)
    public R<Set> removeElementFromSet(String key,String...values){
        Set set = redisZSetService.removeElementFromZSet(key, values);
        return new R<>(set);
    }

    @RequestMapping(value = "/zSet",method = RequestMethod.GET)
    public R<Set> listSet(String key){
        Set set = redisZSetService.getAllElementsFromZSet(key);
        return new R<>(set);
    }

    @RequestMapping(value = "/neededZSet",method = RequestMethod.GET)
    public R<List> getNeededCountElementFromSet(String key,Long neededCount){
        log.info("neededCount{}",neededCount);
        List list = redisZSetService.getNeededCountElementFromZSet(key,neededCount);
        return new R<>(list);
    }

    @RequestMapping(value = "/zSetInRangeOfIndex",method = RequestMethod.GET)
    public R<Set> getElementInIndexRange(String key,long start,long end){
        Set set = redisZSetService.getElementInRangeOfIndex(key,start,end);
        return new R<>(set);
    }
    @RequestMapping(value = "/zSetInRangeOfScore",method = RequestMethod.GET)
    public R<Set> getElementInScoreRange(String key,Double min,Double max){
        log.info("min{},max{}",min,max);
        Set elementInRangeOfScore = redisZSetService.getElementInRangeOfScore(key, min, max);
        return new R<>(elementInRangeOfScore);
    }

    @RequestMapping(value = "/size",method = RequestMethod.GET)
    public R<Long> getSetSize(String key){
        Long set = redisZSetService.getZSetSize(key);
        return new R<>(set);
    }


}
