package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 김대호
 */
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

}
