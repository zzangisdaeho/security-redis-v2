package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.config.security.CurrentAccount;
import com.example.springsecurityredis.config.security.entity.MemberEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/main")
    public String main(@CurrentAccount MemberEntity memberEntity) {
        System.out.println("annotation 작동해??");
        System.out.println(memberEntity);
        System.out.println(memberEntity.getCompanyMembers().get(0).getCompany().getCompanyName());
        return "main";
    }

    @GetMapping("/user/hello")
    public String hello() {
        return "hello";
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
