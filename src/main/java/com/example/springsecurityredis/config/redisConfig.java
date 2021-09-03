package com.example.springsecurityredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

@Configuration
public class redisConfig {

    /**
     * Google Cloud MemoryStore 상에서 notify-keyspace-events 설정시 오류발생하여 무시처리
     *
     * @return
     */
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

}
