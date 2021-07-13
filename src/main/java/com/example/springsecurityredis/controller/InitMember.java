package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.config.security.entity.CompanyEntity;
import com.example.springsecurityredis.config.security.entity.MemberEntity;
import com.example.springsecurityredis.config.security.entity.RoleEntity;
import com.example.springsecurityredis.config.security.entity.SecurityRoles;
import com.example.springsecurityredis.repository.CompanyEntityRepository;
import com.example.springsecurityredis.repository.MemberEntityRepository;
import com.example.springsecurityredis.repository.RoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    //PostConstructor와 Transactional 어노테이션은 spring life cycle 문제에 의해 같이 넣지 못한다.
    //고로 외부 클래스로 만들어주어 호출하는 방식으로 데이터를 넣어준다
    public void init(){
        initMemberService.init();
    }


    @Component
    @RequiredArgsConstructor
    static class InitMemberService{

        private final MemberEntityRepository memberEntityRepository;

        private final RoleEntityRepository roleEntityRepository;

        private final PasswordEncoder passwordEncoder;

        private final CompanyEntityRepository companyEntityRepository;


        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init(){
            RoleEntity role_admin = roleEntityRepository.save(new RoleEntity(SecurityRoles.ADMIN));
            RoleEntity role_user = roleEntityRepository.save(new RoleEntity(SecurityRoles.USER));

            CompanyEntity companyA = new CompanyEntity();
            companyA.setCompanyName("companyA");

            CompanyEntity companyB = new CompanyEntity();
            companyB.setCompanyName("companyB");

            companyEntityRepository.save(companyA);
            companyEntityRepository.save(companyB);


            MemberEntity admin = new MemberEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.addRole(SecurityRoles.ADMIN);
            admin.attachCompany(companyA);
            admin.attachCompany(companyB);


            MemberEntity user = new MemberEntity();
            user.addRole(SecurityRoles.USER);

            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("1234"));
            user.attachCompany(companyA);


            memberEntityRepository.save(admin);
            memberEntityRepository.save(user);
        }
    }
}