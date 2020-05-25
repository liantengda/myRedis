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
public interface RedisSetService {

    /**
     * 向set集合中批量加入数据
     * @param key
     * @param values
     * @return
     */
    Set addElementToSet(String key, Object...values);

    /**
     * 移除集合中一个或多个成员
     * @param key
     * @param values
     * @return
     */
    Set removeElementFromSet(String key,Object...values);

    /**
     * 移除并返回集合中的一个随机元素
     * @param key
     * @return
     */
    JSONObject popElementRandomlyFromSet(String key);

    /**
     * 将一个元素从一个set移动到另一个set
     * @param key
     * @param value
     * @param anotherKey
     * @return
     */
    JSONObject moveElementFromSetToAnotherSet(String key,Object value,String anotherKey);

    /**
     * 获取某个set集合的元素数量
     * @param key
     * @return
     */
    Long getSetSize(String key);

    /**
     * 获取集合中所有成员
     * @param key
     * @return
     */
    Set getAllElementsFromSet(String key);

    /**
     * 获取指定数量的元素集合
     * @param key
     * @param neededCount
     * @return
     */
    Set getNeededCountElementFromSet(String key,long neededCount);
}
