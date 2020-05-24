package com.lian.myredis.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/24 8:51
 */
public class ObjectConvertUtil {
    /**
     * JSON数据，转成Object
     */
    public static  <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
}
