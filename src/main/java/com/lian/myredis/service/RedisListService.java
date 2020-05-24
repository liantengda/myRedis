package com.lian.myredis.service;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 9:06
 */
public interface RedisListService {
    /**
     * 向list中插入数据，从左侧插入。如果键不存在，则创建一个再左侧推入。
     * @param key
     * @return
     */
    List leftPushToList(String key,String value);

    /**
     * 插入一条String数据类型的key-value数据
     * @param key
     * @param values
     * @return
     */
    List leftPushAll(String key, String...values);

    /**
     * 根据key删除一个键值对
     * @param key
     */
    boolean delList(String key);

    /**
     * 右侧push一条数据进入list
     * @param key
     * @param value
     * @return
     */
    List rightPush(String key, String value);

    /**
     * 根据key值获取list的全部内容
     * @param key
     * @return
     */
    List getListContentByKey(String key);

    /**
     * 右侧添加元素数组到list
     * @param key
     * @return
     */
    List rightPushAll(String key, String...values);

    /**
     * 通过元素下标更新list某下标元素
     * @param key
     * @return
     */
    List updateElementByIndex(String key, long index,String value);

    /**
     * 如果key中的值已经存在，则将value追加到旧值后边，如果键不存在，那么
     * @param key
     * @param value
     * @return
     */
    List removeElementByCountAndValue(String key,long count, String value);

    /**
     * 根据key获取list的元素数量
     * @param key
     * @return
     */
    Long getListSize(String key);

    /**
     * 根据下标获取list中指定元素的值
     * @param key
     * @param index
     * @return
     */
    Object getElementByIndex(String key,long index);

    /**
     * 从左测弹出一个值，弹出后不复存在
     * @param key
     * @return
     */
    Object leftPop(String key);

    /**
     * 从右侧弹出一个值，弹出后不复存在
     * @param key
     * @return
     */
    Object rightPop(String key);

}
