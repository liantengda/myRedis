package com.lian.myredis.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 8:48
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplateString;

    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public static final String SPLIT = ":";
    public static final String BOTTOM_LINE = "_";
    public static final String PREFIX_ENTITY_LIKE = "like:entity";
    public static final String PREFIX_ENTITY_MORE_LIKE_USER = "like_entity_user";
    public static final String PREFIX_ENTITY_MORE_LIKE = "like_entity";
    public static final String PREFIX_USER_LIKE = "like:user";



    public Object getObject(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setObject(String key, Object value){
        redisTemplate.opsForValue().set(key,value,DEFAULT_EXPIRE);
    }

    /**
     * 插入字符串数据结构键值对
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public void setString(String key, Object value, long expire,TimeUnit timeUnit){
         redisTemplateString.opsForValue().set(key,toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplateString.expire(key, expire, timeUnit);
        }
    }

    /**
     * 根据key获取字符串值
     * @param key
     * @return
     */
    public String getString(String key){
        String s = redisTemplateString.opsForValue().get(key);
        return s;
    }

    /**
     * 根据key删除字符串
     * @param key
     * @return
     */
    public boolean delString(String key) {
        Boolean delete = redisTemplateString.delete(key);
        return delete;
    }

    /**
     * 根据key，value和offset覆写已存在的记录
     * @param key
     * @param value
     * @param offset
     */
    public void overwriteString(String key,String value,int offset){
        String overWriteResult = null;
        if(offset!=-1){
            redisTemplateString.opsForValue().set(key,value,offset);
        }else {
            redisTemplateString.opsForValue().set(key,value);
        }
    }

    /**
     * 根据部分键，模糊查询键值集合
     * @param partKey
     * @return
     */
    public Map<String,String> getStringListByPartOfKey(String partKey) {
        HashMap<String, String> stringMap = new HashMap<>();
        Set<String> keys = redisTemplateString.keys(partKey);
        for (String key:keys) {
            String string = getString(key);
            stringMap.put(key,string);
        }
        return stringMap;
    }

    /**
     * 根据键，对值进行加法运算--整型的
     * @param key
     * @return
     */
    public Long incrementIntStringByKey(String key, Long summand){
        Long increment = redisTemplateString.opsForValue().increment(key, summand);
        return increment;
    }

    /**
     * 根据键，对值进行加法运算--浮点型的
     * @param key
     * @param summand
     * @return
     */
    public Double incrementDoubleStringByKey(String key,Double summand){
        Double increment = redisTemplateString.opsForValue().increment(key, summand);
        return increment;
    }

    /**
     * 将值添加到旧值得末尾，如果值不存在，则等于set key value
     * @param key
     * @param value
     */
    public void appendStringByKey(String key,String value) {
        redisTemplateString.opsForValue().append(key, value);
    }

    /**
     * 获取String数据结构 -- 键值字节长度
     * @param key
     * @return
     */
    public Long getStringSize(String key){
        Long size = redisTemplateString.opsForValue().size(key);
        return size;
    }


    /**
     * 根据key获取列表长度
     * @param key
     * @return
     */
    public Long getListSize(String key){
        Long size = redisTemplate.opsForList().size(key);
        return size;
    }

    /**
     * 从左侧插入一个元素，如果key不存在，则新建一个list左侧插入
     * @param key
     * @param value
     * @return
     */
    public Long leftPushToList(String key,String value){
        Long aLong = redisTemplate.opsForList().leftPush(key, value);
        return aLong;
    }

    /**
     * 批量将一个数组插入到list中
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAllToList(String key,String...values){
        Long aLong = redisTemplate.opsForList().leftPushAll(key, values);
        return aLong ;
    }

    /**
     * 根据key值查询整个list列表的内容
     * @param key
     * @return
     */
    public List getListContentByKey(String key){
        List range = redisTemplate.opsForList().range(key, 0, -1);
        return range;
    }

    /**
     * 右侧插入数据到list
     * @param key
     * @param value
     * @return
     */
    public Long rightPushToList(String key,String value){
        Long aLong = redisTemplate.opsForList().rightPush(key, value);
        return aLong;
    }

    public Long rightPushAllToList(String key,String...values){
        Long aLong = redisTemplate.opsForList().rightPushAll(key, values);
        return aLong;
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
         String value = (String) redisTemplate.opsForValue().get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : ObjectConvertUtil.fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key) {
        String  value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }



    public void thirtyDays(String key){
        expire(key,DEFAULT_EXPIRE * 30);
    }

    public void expire(String key, long expire) {
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }else {
            redisTemplate.expire(key, DEFAULT_EXPIRE * 3,TimeUnit.SECONDS);
        }
    }

    public void deleteKeys(String key){
        Set<String> keys = redisTemplate.keys(key+"*");
        redisTemplate.delete(keys);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }




    public boolean setNx(String key, String value, long expire) {
        boolean res = redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), value.getBytes());
        if(res){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return res;
    }

    /**
     * redis值自增
     * @param key
     * @param delta
     * @auth xiawenjun
     * @return
     */
    public  Long increment(String key, long delta){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return operations.increment(key, delta);
    }

    // 某个实体的赞
    // oneLikeAndCancelAgain:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, Long entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
    // 某个用户的赞
    // oneLikeAndCancelAgain:user:userId -> int
    public static String getUserLikeKey(Long userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户对于某一实体的多个赞
     * @param bussType
     * @param userId
     * @return
     */
    public static String getEntityMoreLikeUserKey(int bussType,Long userId){
        return PREFIX_ENTITY_MORE_LIKE_USER+BOTTOM_LINE+bussType+BOTTOM_LINE+userId;
    }

    /**
     * 某一实体的获得的赞
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityMoreLikeKey(int entityType, Long entityId) {
        return PREFIX_ENTITY_MORE_LIKE + BOTTOM_LINE + entityType + BOTTOM_LINE + entityId;
    }

    public static void setList(String key,List list){

    }
}
