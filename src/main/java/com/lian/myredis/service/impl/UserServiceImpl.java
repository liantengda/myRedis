package com.lian.myredis.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.lian.myredis.common.bloomFilter.MyBloomFilter;
import com.lian.myredis.common.constant.MyBusinessExceptionEnum;
import com.lian.myredis.common.constant.RedisKeyConstant;
import com.lian.myredis.mapper.UserMapper;
import com.lian.myredis.model.NullValueResultDO;
import com.lian.myredis.model.User;
import com.lian.myredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MyBloomFilter myBloomFilter;


    @Override
    public User findUserIfNotStoreBlank(int id){
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        Object object = redisTemplate.opsForValue().get(RedisKeyConstant.MY_USER+id);
        if(object!=null){
            if(object instanceof NullValueResultDO){
                return null;
            }
            return (User)object;
        }else {
            User user = userMapper.findOneByUserId(id);
            if(user!=null){
                redisTemplate.opsForValue().set(RedisKeyConstant.MY_USER+id,user);
            }else {
                redisTemplate.opsForValue().set(RedisKeyConstant.MY_USER+id,new NullValueResultDO());
                redisTemplate.expire(RedisKeyConstant.MY_USER+id,60,TimeUnit.SECONDS);
            }
            return user;
        }
    }

    @Override
    public User findUserStoreBloomFilter(int id) {
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        Object object = redisTemplate.opsForValue().get(RedisKeyConstant.MY_USER+id);
        if(object!=null){
            return (User)object;
        }else {
            if(!myBloomFilter.contains(String.valueOf(id))){
                return null;
            }
            User user = userMapper.findOneByUserId(id);
            if(user!=null){
                redisTemplate.opsForValue().set(RedisKeyConstant.MY_USER+id,user);
            }
            return user;
        }
    }

    @Override
    public List<User> list() {
        List<User> userList = userMapper.findUserList();
        return userList;
    }

    @Override
    public User add(User user) {
        List<User> existCollect = userMapper.findUserByIdCard(user.getIdCard());
        MyBusinessExceptionEnum.USER_ALREADY_EXIST.assertEquals(existCollect.size(),0);
        User add = userMapper.add(user);
        return add;
    }

    @Override
    public User upd(User user) {
        User upd = userMapper.upd(user);
        return upd;
    }

    @Override
    public User del(Integer id) {
        User user = userMapper.findOneByUserId(id);
        MyBusinessExceptionEnum.USER_ALREADY_DELETED.assertNotNull(user);
        User del = userMapper.del(id);
        return del;
    }

}
