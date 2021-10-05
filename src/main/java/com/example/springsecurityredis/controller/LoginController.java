package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.config.security.dto.LoginInfoDto;
import com.example.springsecurityredis.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/code")
    public RedirectView codeTaker(@RequestParam String code, HttpSession session) throws Exception {

        String redirectPath = loginService.loginUserCompanyList(code, session);

        System.out.println("최종 Redirect Path :" + redirectPath);

        return new RedirectView(redirectPath);
    }

    @GetMapping("/common/joinCompanyList")
    public LoginInfoDto getLoginInfo(HttpSession session){
        return loginService.getInfoDto(session);
    }

}
