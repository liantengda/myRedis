package com.lian.myredis.job;

import com.lian.myredis.common.bloomFilter.MyBloomFilter;
import com.lian.myredis.mapper.UserMapper;
import com.lian.myredis.model.User;
import com.lian.myredis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 20:22
 */
@Component
@EnableScheduling
@Slf4j
public class MyBloomFilterJob {

    @Autowired
    UserService userService;
    @Autowired
    MyBloomFilter myBloomFilter;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void storeUserIdIntoMyBloomFilter(){
        log.info("布伦过滤器定时任务开始--------->");
        List<User> list = userService.list();
        list.forEach(value->myBloomFilter.add(String.valueOf(value.getId())));
    }
}
