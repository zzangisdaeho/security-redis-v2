package com.example.springsecurityredis.config.security.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SecurityRoles {

    ADMIN("관리자"), USER("사용자");

    private String ko;

    SecurityRoles(String ko) {
        this.ko = ko;
    }
}
