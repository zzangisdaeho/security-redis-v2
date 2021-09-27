package com.example.springsecurityredis.service;

import com.example.springsecurityredis.biz.LoginBiz;
import com.example.springsecurityredis.config.security.dto.GoogleToken;
import com.example.springsecurityredis.config.security.dto.GoogleWho;
import com.example.springsecurityredis.config.security.dto.LoginInfoDto;
import com.example.springsecurityredis.config.security.entity.TokenInfoEntity;
import com.example.springsecurityredis.config.security.entity.UserEntity;
import com.example.springsecurityredis.repository.TokenInfoEntityRepository;
import com.example.springsecurityredis.repository.UserEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * 김대호
 * Google Login Service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final LoginBiz loginBiz;

    private final UserEntityRepository userEntityRepository;

    private final TokenInfoEntityRepository tokenInfoEntityRepository;

    /**
     * 김대호
     * 1. Front에서 넘어온 code를 기반으로 Google User info를 특정한다.
     * 2. Token정보를 DB에 저장한다.
     * 3. CompnayList를 반환
     * @param code
     * @return
     * @throws UnirestException
     * @throws JsonProcessingException
     */
    @Transactional
    public LoginInfoDto loginUserCompanyList(String code) throws UnirestException, JsonProcessingException {

        GoogleToken googleToken = loginBiz.tokenTaker(code);

        GoogleWho who = loginBiz.emailGetter(googleToken.getAccess_token());

        // 대표님왈 : Email 값은 고유하지 않을 수 있다
        // 고로 구글에서 조회시 나오는 고유 아이디값을 사용
        UserEntity findUser = userEntityRepository.findByUsername(who.getId())
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 아이디의 유저가 없습니다"));

        tokenInfoEntityRepository.save(new TokenInfoEntity(findUser.getUserSeq(), googleToken.getAccess_token(), googleToken.getRefresh_token()));

        log.info("code : {}"
                +"\n ========= token info : =========== : {} "
                + "\n ========== who : ========== : {}",code, googleToken, who);

        return new LoginInfoDto(findUser.getUserSeq(),
                findUser.getUsername(),
                findUser.getCompanyList().stream().map(LoginInfoDto.CompanyListDto::new).collect(Collectors.toList()));
    }
}
