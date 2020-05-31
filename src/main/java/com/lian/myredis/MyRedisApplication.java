package com.lian.myredis;

import com.lian.myredis.common.bloomFilter.MyBloomFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRedisApplication.class, args);
    }

}
