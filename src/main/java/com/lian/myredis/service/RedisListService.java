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
    boolean delStringKeyValue(String key);

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
     * 根据key对值进行加法运算,一定要注意，值一定要是整数。
     * @param key
     * @return
     */
    List rightPushAll(String key, String...values);

    /**
     * 根据key对值进行加法运算，一定要注意，值一定要是double类型
     * @param key
     * @return
     */
    Map<String,Double> incrementDoubleValueByKey(String key, Double summand);

    /**
     * 如果key中的值已经存在，则将value追加到旧值后边，如果键不存在，那么
     * @param key
     * @param value
     * @return
     */
    Map<String,String> appendStringKeyValue(String key, String value);

    /**
     * 根据key获取list的元素数量
     * @param key
     * @return
     */
    Long getListSize(String key);

}
