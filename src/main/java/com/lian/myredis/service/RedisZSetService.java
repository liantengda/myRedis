package com.lian.myredis.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 20:24
 */
public interface RedisZSetService {

    /**
     * 添加一个有序集合
     * @param key
     * @param value
     * @param score
     * @return
     */
    Set addElementToZSet(String key, Object value,double score);

    /**
     * 移除集合中一个或多个成员
     * @param key
     * @param values
     * @return
     */
    Set removeElementFromZSet(String key, Object... values);

    /**
     * 添加一组数据到ZSet。
     * @param key
     * @param values
     * @return
     */
    Set addElementSetToZSet(String key,Map<String ,Double> values);

    /**
     * 获取某成员的排序序号
     * @param key
     * @return
     */
    JSONObject getElementRank(String key,Object o);

    /**
     * 从某个ZSet中获取指定范围内的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set getElementInRangeOfIndex(String key, long start, long end);

    /**
     * 从某个ZSet中获取指定范围内的元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set getElementInRangeOfScore(String key, double min,double max);

    /**
     * 获取某个set集合的元素数量
     * @param key
     * @return
     */
    Long getZSetSize(String key);

    /**
     * 获取集合中所有成员
     * @param key
     * @return
     */
    Set getAllElementsFromZSet(String key);

    /**
     * 从某个ZSet中获取指定数量的元素集合
     * @param key
     * @param neededCount
     * @return
     */
    List getNeededCountElementFromZSet(String key, long neededCount);
}
