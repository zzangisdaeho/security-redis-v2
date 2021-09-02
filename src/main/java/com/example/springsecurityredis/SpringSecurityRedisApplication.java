package com.example.springsecurityredis;

import com.example.springsecurityredis.config.security.entity.RoleEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableRedisHttpSession
public class SpringSecurityRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRedisApplication.class, args);

    }

}
