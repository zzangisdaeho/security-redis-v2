package com.example.springsecurityredis.biz;

import com.example.springsecurityredis.config.security.dto.GoogleToken;
import com.example.springsecurityredis.config.security.dto.GoogleWho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 김대호
 * Google Oauth2.0 Login을 위한 핵심 BIZ
 */
@Service
@Slf4j
public class LoginBiz {

    private final ObjectMapper objectMapper;

    private String client_id;
    private String client_secret;

    //0번째 항목의 8080/code 로 callBack 받는다.
    private String redirect_uri;
    private String grant_type;
    private String token_uri;

    private String info_uri;

    public LoginBiz(ObjectMapper objectMapper, JSONObject clientSecret) throws IOException {
        this.objectMapper = objectMapper;

        client_id = clientSecret.getString("client_id");
        client_secret = clientSecret.getString("client_secret");
        redirect_uri = (String) clientSecret.getJSONArray("redirect_uris").get(0);
        grant_type = "authorization_code";
        token_uri = clientSecret.getString("token_uri");
        info_uri = "https://www.googleapis.com/oauth2/v1/userinfo";

        log.info("Google Oauth token_uri = {}", token_uri);
        log.info("Google Oauth redirect_uri = {}", redirect_uri);
    }

    /**
     * 김대호
     * Authorization code로 token을 받아옴
     * @param code
     * @return
     * @throws UnirestException
     * @throws JsonProcessingException
     */
    public GoogleToken tokenTaker(String code) throws UnirestException, JsonProcessingException {

        // https://accounts.google.com/o/oauth2/token 는 예전버전으로 추측됨
        String response = Unirest.post(token_uri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("code", code)
                .field("client_id", client_id)
                .field("client_secret", client_secret)
                .field("redirect_uri", redirect_uri)
                .field("grant_type", grant_type)
                .asString()
                .getBody();

        return objectMapper.readValue(response, GoogleToken.class);
    }

    /**
     * 김대호
     * token을 통해 Google 유저 계정 정보를 가져옴
     * @param token
     * @return
     * @throws UnirestException
     * @throws JsonProcessingException
     */
    public GoogleWho emailGetter(String token) throws UnirestException, JsonProcessingException {

        GetRequest getRequest = Unirest.get(info_uri + "?access_token=" + token);
        return objectMapper.readValue(getRequest.asString().getBody(), GoogleWho.class);
    }
}
