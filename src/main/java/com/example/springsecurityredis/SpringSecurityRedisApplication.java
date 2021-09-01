package com.example.springsecurityredis;

import com.example.springsecurityredis.config.security.entity.RoleEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRedisApplication.class, args);

    }

}
