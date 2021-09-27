package com.example.springsecurityredis.config;

import com.example.springsecurityredis.controller.LoginController;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.Objects;

@Configuration
public class BeanConfig {

    /**
     * 김대호
     * ObjectMapper 등록. unknown property에 대한 fail하지 않도록 설정.
     * DTO transport를 위해 사용.
     * @return
     */
    @Bean
    public ObjectMapper registryObjectMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 김대호
     * Google client 스펙에 맞춘 Json parser? 를 등록해줌
     * Credential 제작과, API들을 호출하기 위한 객체를 만들때 넣어줌
     * @return
     */
    @Bean
    public JsonFactory googleJsonFactory(){
        return JacksonFactory.getDefaultInstance();
    }

    /**
     * 김대호
     * Client Secret json 파일을 JSON Object화 하여 등록
     * @return
     */
    @SneakyThrows
    @Bean
    public JSONObject clientSecret(){
        return new JSONObject(
                FileCopyUtils.copyToString(
                        new InputStreamReader(
                                Objects.requireNonNull(
                                        LoginController.class.getClassLoader().getResourceAsStream("client_secret_test.json")
                                )
                        ))).getJSONObject("web");
    }
}
