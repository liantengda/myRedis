package com.lian.myredis.service;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 9:06
 */
public interface RedisStringService {
    /**
     * 根据key获取String数据类型的key-value
     * @param key
     * @return
     */
    String getStringKeyValue(String key);

    /**
     * 插入一条String数据类型的key-value数据
     * @param key
     * @param value
     * @return
     */
    Map<String,String> setStringKeyValue(String key,String value);

    /**
     * 根据key删除一个键值对
     * @param key
     */
    boolean delStringKeyValue(String key);

    /**
     * 覆写key对应的value值，当offset为-1时，则整个覆盖。否则只覆写指定位置
     * 此处需要注意，UTF-8汉字占用3个字节，如果偏移量不控制再3的倍数，就会出现乱码
     * @param key
     * @param value
     * @param offset
     * @return
     */
    Map<String,String> overwriteStringKyValue(String key,String value,int offset);

    /**
     * 根据部分key，模糊查询键值集合,入参一定要加上通配符 *  例如：user_string*
     * 如果不加通配符，什么都不会查出来。
     * @param partKey
     * @return
     */
    Map<String,String> getStringMapByPartOfKey(String partKey);

    /**
     * 根据key对值进行加法运算,一定要注意，值一定要是整数。
     * @param key
     * @return
     */
    Map<String,Long> incrementIntValueByKey(String key,long summand);

    /**
     * 根据key对值进行加法运算，一定要注意，值一定要是double类型
     * @param key
     * @return
     */
    Map<String,Double> incrementDoubleValueByKey(String key,Double summand);

    /**
     * 如果key中的值已经存在，则将value追加到旧值后边，如果键不存在，那么
     * @param key
     * @param value
     * @return
     */
    Map<String,String> appendStringKeyValue(String key,String value);

    /**
     * 根据key获取value的字节长度
     * @param key
     * @return
     */
    Long getStringSize(String key);

}
