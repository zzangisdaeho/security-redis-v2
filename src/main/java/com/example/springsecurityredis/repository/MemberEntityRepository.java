package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.MemberEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

/**
 * 김대호
 */
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long> {

//    @EntityGraph(attributePaths = {"companyMembers"})
//    Optional<MemberEntity> findByUsername(String username);

    Optional<MemberEntity> findByUserUserSeqAndCompanyCompanySeq(long userSeq, long companySeq);
}
