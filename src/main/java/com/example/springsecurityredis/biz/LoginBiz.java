package com.example.springsecurityredis.biz;

import com.example.springsecurityredis.config.security.dto.GoogleToken;
import com.example.springsecurityredis.config.security.dto.GoogleWho;
import com.example.springsecurityredis.controller.LoginController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Service
@Slf4j
public class LoginBiz {

    private final ObjectMapper objectMapper;

    private JSONObject web = new JSONObject(
            FileCopyUtils.copyToString(
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    LoginController.class.getClassLoader().getResourceAsStream("client_secret_test.json")
                            )
                    ))).getJSONObject("web");

    private String client_id = web.getString("client_id");
    private String client_secret = web.getString("client_secret");

    //0번째 항목의 8080/code 로 callBack 받는다.
    private String redirect_uri = (String) web.getJSONArray("redirect_uris").get(0);
    private String grant_type = "authorization_code";
    private String token_uri = web.getString("token_uri");

    private String info_uri = "https://www.googleapis.com/oauth2/v1/userinfo";

    public LoginBiz(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        log.info("Google Oauth token_uri = {}", token_uri);
        log.info("Google Oauth redirect_uri = {}", redirect_uri);
    }

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

    public GoogleWho emailGetter(String token) throws UnirestException, JsonProcessingException {

        GetRequest getRequest = Unirest.get(info_uri + "?access_token=" + token);
        return objectMapper.readValue(getRequest.asString().getBody(), GoogleWho.class);
    }
}
