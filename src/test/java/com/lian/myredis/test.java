package com.lian.myredis;

import com.lian.myredis.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 8:49
 */
@SpringBootTest
public class test {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisTemplate<String,String> redisStringTemplate;

    @Test
    public void setObject() {
         redisTemplate.opsForValue().set("my_second_key","hehe");
        Object my_second_key = redisTemplate.opsForValue().get("my_second_key");
        System.out.println(my_second_key);
    }

    @Test
    public void setString() {
        redisStringTemplate.opsForValue().set("my_string_key","hahahah");
        String my_string_key = redisStringTemplate.opsForValue().get("my_string_key");
        System.out.println(my_string_key);
    }


}
