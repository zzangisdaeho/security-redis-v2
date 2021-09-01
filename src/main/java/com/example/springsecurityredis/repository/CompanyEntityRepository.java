package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

}
