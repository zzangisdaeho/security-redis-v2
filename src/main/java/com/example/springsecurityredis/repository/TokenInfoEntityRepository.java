package com.example.springsecurityredis.repository;

import com.example.springsecurityredis.config.security.entity.TokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 김대호
 */
public interface TokenInfoEntityRepository extends JpaRepository<TokenInfoEntity, Long> {

    /**
     * Token 중 AccessToken 값만 업데이트 하기 위한 쿼리
     * 업데이트 후 1차 영속성 clear option true
     * @param userSeq
     * @param accessToken
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query("update TokenInfoEntity t set t.accessToken = :accessToken where t.userSeq = :userSeq")
    int accessTokenOnlyUpdate(@Param("userSeq") long userSeq, @Param("accessToken") String accessToken);
}
