package com.example.springsecurityredis.config.security.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoleEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private SecurityRoles securityRoles;

    @Getter
    public enum SecurityRoles {

        ADMIN("관리자"), USER("사용자");

        private String ko;

        SecurityRoles(String ko) {
            this.ko = ko;
        }
    }
}
