package com.lian.myredis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 20:24
 */
public interface RedisHashService {


    Map putIntoHash(String key,String hashKey,String value);

    List getAllValuesByKey(String key);

    Map getAllKeyValueByKey(String key);

    Map getNeedCountHashKeyValue(String key,long needCount);

    Map putAllIntoHash(String key, Map<String,String> map);

    Long getHashElementSize(String key);

    Set getHashKeys(String key);

    Object getValueByKeyAndHashKey(String key,String hashKey);

    Boolean hasHashKey(String key,String hashKey);

    Long deleteKeys(String key, String...hashKeys);
}
