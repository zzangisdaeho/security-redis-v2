package com.example.springsecurityredis.join_company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinCompanyEntityRepository extends JpaRepository<JoinCompanyEntity, Long> {

    List<JoinCompanyEntity> findByCompanySeq(long companySeq);
}
