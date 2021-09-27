package com.example.springsecurityredis.service;

import com.example.springsecurityredis.config.security.entity.TokenInfoEntity;
import com.example.springsecurityredis.repository.TokenInfoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 김대호
 * Google Token 을 Caching하는 Service
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenInfoEntityRepository tokenInfoEntityRepository;

    /**
     * 김대호
     * DB에 있는 Token값 caching하여 load. key값으로 UserSequence 사용
     * @param userSeq
     * @return
     */
    @Cacheable(value = "tokens", key = "#userSeq")
    public TokenInfoEntity getToken(long userSeq){
        TokenInfoEntity tokenInfoEntity = tokenInfoEntityRepository.findById(userSeq).orElseThrow(() -> new UsernameNotFoundException("없는 유저시퀀스입니다."));

        System.out.println("========cacheable called=========");
        System.out.println(tokenInfoEntity);

        return tokenInfoEntity;
    }

    /**
     * 김대호
     * DB에 있는 Token값 update 후 caching. key값으로 UserSequence 사용
     * @param tokenInfoEntity
     * @return
     */
    @CachePut(value = "tokens", key = "#tokenInfoEntity.userSeq")
    public TokenInfoEntity updateToken(TokenInfoEntity tokenInfoEntity){
        return tokenInfoEntityRepository.save(tokenInfoEntity);
    }

    /**
     * caching한 Token값 삭제
     * @param userSeq
     */
    @CacheEvict(value = "tokens", key = "#userSeq")
    public void deleteToken(long userSeq){
        tokenInfoEntityRepository.deleteById(userSeq);
    }
}
