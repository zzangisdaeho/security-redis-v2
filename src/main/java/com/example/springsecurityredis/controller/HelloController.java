package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.config.security.CurrentAccount;
import com.example.springsecurityredis.config.security.CustomUser;
import com.example.springsecurityredis.config.security.entity.MemberEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HelloController {

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
}
