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
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

/**
 * 김대호
 * Google Login Service
 */
@Service
@Slf4j
public class LoginService {

    private final LoginBiz loginBiz;

    private final UserEntityRepository userEntityRepository;

    private final TokenInfoEntityRepository tokenInfoEntityRepository;

    private final PasswordEncoder passwordEncoder;

    private String scopeDrive;
//    private String scopeDocs;
    private String scopeCalendar;
    private String scopeUserEmail;
    private String scopeUserProfile;

    private String forceURL;

    public LoginService(LoginBiz loginBiz, UserEntityRepository userEntityRepository, TokenInfoEntityRepository tokenInfoEntityRepository, PasswordEncoder passwordEncoder, JSONObject clientSecret) {
        this.loginBiz = loginBiz;
        this.userEntityRepository = userEntityRepository;
        this.tokenInfoEntityRepository = tokenInfoEntityRepository;
        this.passwordEncoder = passwordEncoder;

        scopeDrive = "https://www.googleapis.com/auth/drive";
//        scopeDocs = "https://www.googleapis.com/auth/documents";
        scopeCalendar = "https://www.googleapis.com/auth/calendar";
        scopeUserEmail = "https://www.googleapis.com/auth/userinfo.email";
        scopeUserProfile = "https://www.googleapis.com/auth/userinfo.profile";

        forceURL = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + clientSecret.getString("client_id")
                + "&response_type=code"
                + "&scope="
                + scopeUserEmail + " "
                + scopeUserProfile + " "
                + scopeDrive + " "
                + scopeCalendar
                + "&redirect_uri="
                + (String) clientSecret.getJSONArray("redirect_uris").get(0)
                + "&access_type=offline"
                + "&approval_prompt=force";
    }

    /**
     * 김대호
     * 1. Front에서 넘어온 code를 기반으로 Google User info를 특정한다.
     * 2. Token정보를 DB에 저장한다.
     * 3. Token정보에 Refresh 값이 null일 경우 force 옵션으로 redirect한다
     * 4. Token정보에 Refresh 값이 들어있을 경우 CompanyList를 보여주는 URL로 redircect 한다.
     * @param code
     * @return
     * @throws UnirestException
     * @throws JsonProcessingException
     */
    @Transactional
    public String loginUserCompanyList(String code, HttpSession session) throws Exception {

        GoogleToken googleToken = loginBiz.tokenTaker(code);

        GoogleWho who = loginBiz.emailGetter(googleToken.getAccess_token());

        // 김대호
        // 대표님왈 : Email 값은 고유하지 않을 수 있다
        // 고로 구글에서 조회시 나오는 고유 아이디값을 사용
        // 유저가 존재하지 않을시 자동 회원가입 시킨다.
        UserEntity findUser = userEntityRepository.findByUsername(who.getId())
                .orElseGet(() -> userEntityRepository.save(UserEntity.builder()
                        .username(who.getId())
                        .name(who.getName())
                        .mail(who.getEmail())
                        .password(passwordEncoder.encode(who.getId()))
                        .build()));

        // Refresh Token값이 없는 경우 AccessToken만 업데이트
        if(googleToken.getRefresh_token() != null){
            System.out.println("====== 모든 토큰값 저장=======");
            tokenInfoEntityRepository.save(new TokenInfoEntity(findUser.getUserSeq(), googleToken.getAccess_token(), googleToken.getRefresh_token()));
        }else{
            System.out.println("====== 엑세스 토큰값만 저장=======");
            tokenInfoEntityRepository.accessTokenOnlyUpdate(findUser.getUserSeq(), googleToken.getAccess_token());
        }

        String redirectPath = null;

        TokenInfoEntity tokenInfo = tokenInfoEntityRepository.findById(findUser.getUserSeq()).orElseThrow(() -> new Exception("토큰 저장값 없음"));
        //최종 토큰 상태에서 refresh token 값이 null이면 force권한으로 재요청하기위한 프로세스를 타야한다.
        redirectPath = (tokenInfo.getRefreshToken() == null)? forceURL : "http://localhost:8080/common/joinCompanyList";

        log.info("code : {}"
                +"\n ========= token info : =========== : {} "
                +"\n ========= access token : ============= : {}"
                +"\n ========= refresh token : ============= : {}"
                + "\n ========== who : ========== : {}"
                ,code, tokenInfo, tokenInfo.getAccessToken(), tokenInfo.getRefreshToken(), who);

        session.setAttribute("who", who.getId());

        return redirectPath;
    }

    @Transactional(readOnly = true)
    public LoginInfoDto getInfoDto(HttpSession session){

        UserEntity findUser = userEntityRepository.findByUsername((String)session.getAttribute("who")).get();

        return new LoginInfoDto(findUser.getUserSeq(),
                findUser.getUsername(),
                findUser.getCompanyList().stream().map(LoginInfoDto.CompanyListDto::new).collect(Collectors.toList()));
    }

}
