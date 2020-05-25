package com.lian.myredis.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
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
        redisTemplate.opsForValue().set(key,value);
    }

    /*----------------------------------------------String------------------------------------------------------------*/
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

    /*--------------------------------------list----------------------------------------------------------------------*/

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

    /**
     * 右侧插入元素数组到list
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAllToList(String key,String...values){
        Long aLong = redisTemplate.opsForList().rightPushAll(key, values);
        return aLong;
    }

    /**
     * 修改list指定位置的值
     * @param key
     * @param index
     * @param value
     */
    public void setElementByIndex(String key,long index,String value){
        redisTemplate.opsForList().set(key,index,value);
    }

    /**
     * 根据count从list中移除元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long removeFromList(String key,long count,String value){
        Long remove = redisTemplate.opsForList().remove(key, count, value);
        return remove;
    }

    /**
     * 根据给定下标获取redis中的值
     * @param key
     * @param index
     * @return
     */
    public Object getElementByIndex(String key,long index){
        Object index1 = redisTemplate.opsForList().index(key, index);
        return index1;
    }

    /**
     * 从左侧弹出一个元素，弹出的元素将不复存在
     * @param key
     * @return
     */
    public Object leftPopFromList(String key){
        Object o = redisTemplate.opsForList().leftPop(key);
        return o;
    }

    /**
     * 从右侧弹出一个元素，弹出的元素将不复存在
     * @param key
     * @return
     */
    public Object rightPopFromList(String key){
        Object o = redisTemplate.opsForList().rightPop(key);
        return o;
    }

    /**
     * 根据key删除某元素
     * @param key
     * @return
     */
    public Boolean deleteObject(String key){
        Boolean delete = redisTemplate.delete(key);
        return delete;
    }


    /*------------------------------------------hash------------------------------------------------------------------*/

    /**
     * 向某个散列表中插入一个值
     * @param key
     * @param hashKey
     * @param value
     */
    public void putIntoHash(String key,String hashKey,String value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * 获取某个hash表中所有键的值
     * @param key
     * @return
     */
    public List getAllValuesByKey(String key){
        List values = redisTemplate.opsForHash().values(key);
        return values;
    }

    /**
     * 获取某个hash表中所有的键值对
     * @param key
     * @return
     */
    public Map getAllKeyValueByKey(String key){
        Map entries = redisTemplate.opsForHash().entries(key);
        return  entries;
    }

    /**
     * 根据需要的键值对数量，利用hash迭代器，找出对应数量的键值并返回
     * @param key
     * @param needCount
     * @return
     */
    public Map iteratorHash(String key,long needCount){
        HashMap<Object, Object> needMap = new HashMap<>();
        Cursor<Map.Entry<Object,Object>> scan = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (scan.hasNext()&&needCount>0){
            Map.Entry<Object, Object> next = scan.next();
            needMap.put(next.getKey(),next.getValue());
            needCount--;
        }
       return needMap;
    }

    /**
     * 向某个散列表中直接插入n个值
     * @param key
     * @param map
     */
    public void putAllIntoHash(String key, Map<String,String> map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    /**
     * 哈希拥有的元素数量
     * @param key
     * @return
     */
    public Long getHashElementSize(String key){
        Long size = redisTemplate.opsForHash().size(key);
        return size;
    }

    /**
     * 哈希拥有的键集合
     * @param key
     * @return
     */
    public Set getHashKeys(String key){
        Set keys = redisTemplate.opsForHash().keys(key);
        return keys;
    }

    /**
     * 从某hash表中获取给定的hashKey的值
     * @param key
     * @param hashKey
     * @return
     */
    public Object getValueByKeyAndHashKey(String key,String hashKey){
        Object o = redisTemplate.opsForHash().get(key, hashKey);
        return o;
    }

    /**
     * 判断某hashKey是否存在
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hasHashKey(String key,String hashKey){
        Boolean aBoolean = redisTemplate.opsForHash().hasKey(key, hashKey);
        return aBoolean;
    }

    /**
     * 批量删除一组hashKey
     * @param key
     * @param hashKeys
     * @return
     */
    public Long deleteKeys(String key, String...hashKeys){
        Long delete = redisTemplate.opsForHash().delete(key, hashKeys);
        return delete;
    }

    /*------------------------------------------------set数据结构------------------------------------------------------*/

    /**
     * 无序集合set中添加元素
     * @param key
     * @param values
     * @return
     */
    public Long addElementToSet(String key,Object...values){
        Long add = redisTemplate.opsForSet().add(key, values);
        return add;
    }

    /**
     * 移除set集合中一个或者多个元素
     * @param key
     * @param values
     * @return
     */
    public Long removeFromSet(String key,Object...values){
        Long remove = redisTemplate.opsForSet().remove(key, values);
        return remove;
    }

    /**
     * 移除并返回集合中的一个随机元素
     * @param key
     * @return
     */
    public Object popFromSet(String key){
        Object pop = redisTemplate.opsForSet().pop(key);
        return pop;
    }

    /**
     * 将一个元素从一个set集合移动到另一个set集合
     * @param key
     * @param o
     * @param anotherKey
     * @return
     */
    public Boolean moveElementFromSetToAnotherSet(String key,Object o,String anotherKey){
        Boolean move = redisTemplate.opsForSet().move(key, o, anotherKey);
        return move;
    }

    /**
     * 获取无序集合set的元素数量
     * @param key
     * @return
     */
    public Long getSetSize(String key){
        Long size = redisTemplate.opsForSet().size(key);
        return size;
    }

    /**
     * 获取set集合中所有元素
     * @param key
     * @return
     */
    public Set getAllElementFromSet(String key){
        Set members = redisTemplate.opsForSet().members(key);
        return members;
    }

    /**
     * 从set集合中获取指定数量的元素
     * @param key
     * @param neededCount
     * @return
     */
    public Set getNeededCountFromSet(String key,Long neededCount){
        Cursor<Object> scan = redisTemplate.opsForSet().scan(key, ScanOptions.NONE);
        HashSet<Object> objects = new HashSet<>();
        while (scan.hasNext()&&neededCount>0){
            Object next = scan.next();
            objects.add(next);
            neededCount--;
        }
        return objects;
    }

    /*------------------------------------------------------zSet集合--------------------------------------------------*/

    /**
     * 向zSet集合中添加一个元素，如果存在则返回false，不存在则返回true
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean addElementToZSet(String key,String value,double score){
        Boolean add = redisTemplate.opsForZSet().add(key, value, score);
        return add;
    }

    /**
     * 向zSet中新增一个有序集合
     * @param key
     * @param tuples
     * @return
     */
    public Long addElementSetToZSet(String key, Set<ZSetOperations.TypedTuple<Object>> tuples){
        Long add = redisTemplate.opsForZSet().add(key, tuples);
        return add;
    }

    /**
     * 从有序集合zSet中移除一个或多个元素
     * @param key
     * @param values
     * @return
     */
    public Long removeFromZSet(String key,Object...values){
        Long remove = redisTemplate.opsForZSet().remove(key, values);
        return remove;
    }

    /**
     * 返回有序集合中指定成员的排名
     * @param key
     * @param o
     * @return
     */
    public Long getRankZSet(String key,Object o){
        Long rank = redisTemplate.opsForZSet().rank(key, o);
        return rank;
    }

    /**
     * 返回有序集合中指定区间内的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set getElementsInRange(String key,long start,long end){
        Set range = redisTemplate.opsForZSet().range(key, start, end);
        return range;
    }

    /**
     * 返回指定区间成员
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set getElementCountInRange(String key,double min,double max){
        Set set = redisTemplate.opsForZSet().rangeByScore(key, min, max);
        return set;
    }

    /**
     * 返回有序集合的成员个数
     * @param key
     * @return
     */
    public Long getElementCount(String key){
        Long size = redisTemplate.opsForZSet().size(key);
        return size;
    }

    /**
     * 获取只当成员的score
     * @param key
     * @param o
     * @return
     */
    public Double getSpecificScoreFromZSet(String key,Object o){
        Double score = redisTemplate.opsForZSet().score(key, o);
        return score;
    }

    /**
     * 移除指定索引位置的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long removeFromZSetInRange(String key,long start,long end){
        Long aLong = redisTemplate.opsForZSet().removeRange(key, start, end);
        return aLong;
    }

    /**
     * 遍历zSet集合
     * @param key
     * @param neededCount
     * @return
     */
    public List iteratorZSet(String key,long neededCount){
        ArrayList<Object> arrayList = new ArrayList<>();
        Cursor<ZSetOperations.TypedTuple<Object>> scan = redisTemplate.opsForZSet().scan(key, ScanOptions.NONE);
        while (scan.hasNext()&&neededCount>0){
            ZSetOperations.TypedTuple<Object> next = scan.next();
            arrayList.add(next);
            neededCount--;
        }
        return arrayList;
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
