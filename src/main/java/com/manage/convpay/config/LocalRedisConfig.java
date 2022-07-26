package com.manage.convpay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class LocalRedisConfig {
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;
    @PostConstruct
    public void startReids(){ // name is free
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy // application close
    public void stopRedis(){
        if(redisServer != null){
            redisServer.stop();
        }
    }
}
