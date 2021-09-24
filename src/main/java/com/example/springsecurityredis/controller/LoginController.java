package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.biz.LoginBiz;
import com.example.springsecurityredis.config.security.dto.GoogleToken;
import com.example.springsecurityredis.config.security.dto.GoogleWho;
import com.example.springsecurityredis.config.security.dto.LoginInfoDto;
import com.example.springsecurityredis.config.security.entity.UserEntity;
import com.example.springsecurityredis.repository.UserEntityRepository;
import com.example.springsecurityredis.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final UserEntityRepository userEntityRepository;

    @GetMapping("/code")
    public LoginInfoDto codeTaker(@RequestParam String code) throws UnirestException, JsonProcessingException {

        return loginService.loginUserCompanyList(code);
    }


}
