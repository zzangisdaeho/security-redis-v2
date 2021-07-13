package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.RoleEntity;
import com.example.springsecurityredis.config.security.entity.SecurityRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, SecurityRoles> {
}
