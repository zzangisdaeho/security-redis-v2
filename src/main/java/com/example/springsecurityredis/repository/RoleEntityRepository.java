package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.example.springsecurityredis.config.security.entity.RoleEntity.*;

/**
 * 김대호
 */
public interface RoleEntityRepository extends JpaRepository<RoleEntity, SecurityRoles> {
}
