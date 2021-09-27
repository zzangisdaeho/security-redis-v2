package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.config.security.CurrentAccount;
import com.example.springsecurityredis.config.security.CustomUser;
import com.example.springsecurityredis.config.security.entity.MemberEntity;
import com.example.springsecurityredis.config.security.entity.TokenInfoEntity;
import com.example.springsecurityredis.service.GoogleDriveService;
import com.example.springsecurityredis.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final TokenService tokenService;

    private final GoogleDriveService googleDriveService;

    @GetMapping("/")
    public String index(@CurrentAccount CustomUser.WhoAreYou who, HttpSession session) {
        return session.getId() + "\nHello " +"\n" + who;
    }

    @GetMapping("/main")
    public String main(@CurrentAccount CustomUser.WhoAreYou who, HttpSession session) {
        System.out.println("annotation 작동해??");
        System.out.println(who);

        return "THIS IS MAIN " +
                "\n # your session id = " + session.getId() +
                "\n # you are = " + who;
    }

    @GetMapping("/user/hello")
    public String hello(@CurrentAccount CustomUser.WhoAreYou who, HttpSession session){
        return "HELLO!!!! " +
                "\n # your session id = " + session.getId() +
                "\n # you are = " + who;
    }

    @GetMapping("/admin/hello")
    public String helloAdmin() {
        return "hello Admin";
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }

    @GetMapping("/bye")
    public String bye() {
        return "byebye";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test(){
        return "test통과";
    }

    @GetMapping("/token/{userSeq}")
    public TokenInfoEntity getToken(@PathVariable long userSeq){
        TokenInfoEntity token = tokenService.getToken(userSeq);

        System.out.println("========token=========");
        System.out.println(token);

        return token;
    }

    @GetMapping("/driveList/{userSeq}")
    public void getDriveList(@PathVariable long userSeq) throws GeneralSecurityException, IOException {
        googleDriveService.getDriveListSample(userSeq);
    }
}
