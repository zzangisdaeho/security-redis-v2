package com.example.springsecurityredis.config.security.entity;

import com.example.springsecurityredis.repository.CompanyEntityRepository;
import com.example.springsecurityredis.repository.MemberEntityRepository;
import com.example.springsecurityredis.repository.RoleEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@SpringBootTest
class MemberEntityTest {

    @Autowired
    MemberEntityRepository memberEntityRepository;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    CompanyEntityRepository companyEntityRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    @Transactional
    @Commit
    public void test1(){
        RoleEntity role_admin = roleEntityRepository.save(new RoleEntity(SecurityRoles.ADMIN));
        RoleEntity role_user = roleEntityRepository.save(new RoleEntity(SecurityRoles.USER));

        CompanyEntity companyA = new CompanyEntity();
        companyA.setCompanyName("companyA");

        CompanyEntity companyB = new CompanyEntity();
        companyB.setCompanyName("companyB");

        companyEntityRepository.save(companyA);
        companyEntityRepository.save(companyB);

        em.flush();
        em.clear();
    }

    @Test
    @Transactional
    @Commit
    public void test2() throws Exception {

        CompanyEntity companyA = companyEntityRepository.findByCompanyName("companyA").orElseThrow(() -> new Exception("그딴 회사 없다"));
        CompanyEntity companyB = companyEntityRepository.findByCompanyName("companyB").orElseThrow(() -> new Exception("그딴 회사 없다"));

        MemberEntity admin = new MemberEntity();
        admin.addRole(SecurityRoles.ADMIN);
        admin.attachCompany(companyA);
        admin.attachCompany(companyA);
        admin.attachCompany(companyB);


        MemberEntity user = new MemberEntity();
        user.addRole(SecurityRoles.USER);
        user.addRole(SecurityRoles.USER);
        user.removeRole(SecurityRoles.ADMIN);
        user.attachCompany(companyB);

        memberEntityRepository.save(admin);
        memberEntityRepository.save(user);

    }
}