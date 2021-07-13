package com.example.springsecurityredis.config.security;

import com.example.springsecurityredis.config.security.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class CustomUser extends User {

    private MemberEntity memberEntity;

    private List<String> companyList;

    public CustomUser(MemberEntity memberEntity, Collection<? extends GrantedAuthority> authorities) {
        super(memberEntity.getUsername(), memberEntity.getPassword(), authorities);
        this.memberEntity = memberEntity;
        this.companyList = memberEntity.getCompanyMembers().stream().map(e -> e.getCompany().getCompanyName()).collect(Collectors.toList());
    }
}
