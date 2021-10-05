package com.example.springsecurityredis.proposal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalEntityRepository extends JpaRepository<ProposalEntity, Long> {
}
