package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.TokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 김대호
 */
public interface TokenInfoEntityRepository extends JpaRepository<TokenInfoEntity, Long> {
}
