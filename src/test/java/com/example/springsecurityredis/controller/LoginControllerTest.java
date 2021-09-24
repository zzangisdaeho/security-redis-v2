package com.example.springsecurityredis.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginControllerTest {

    @Value("classpath:/client_secret_test.json")
    private Resource clientSecret;

    @Test
    void secretJsonLoadTest() throws IOException {
        InputStreamReader reader = new InputStreamReader(clientSecret.getInputStream());
        String s = FileCopyUtils.copyToString(reader);

        JSONObject jsonObject = new JSONObject(s);
        JSONObject web = jsonObject.getJSONObject("web");
        String id = web.getString("client_id");
        String secret = web.getString("client_secret");
        JSONArray redirectArray = web.getJSONArray("redirect_uris");

        Assertions.assertEquals("508138974042-3lp9vp9kqo37239hnlrhr0tbhvh5p6ca.apps.googleusercontent.com", id);
        Assertions.assertEquals("kx6-khx6EymYipLBXTuUELa_", secret);
        Assertions.assertEquals("http://localhost:8080/code", redirectArray.get(0));
    }

    @Test
    void wtf() throws IOException {
        JSONObject web = new JSONObject(
                FileCopyUtils.copyToString(
                new InputStreamReader(
                        Objects.requireNonNull(
                                LoginControllerTest.class.getClassLoader().getResourceAsStream("client_secret_test.json")
                        )
                        ))).getJSONObject("web");

        String client_id = web.getString("client_id");
        String client_secret = web.getString("client_secret");
        String redirect_uri = (String) web.getJSONArray("redirect_uris").get(0);
        String grant_type = "authorization_code";
        String token_uri = web.getString("token_uri");
    }
}