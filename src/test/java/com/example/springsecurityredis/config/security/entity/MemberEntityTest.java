package com.example.springsecurityredis.config.security.entity;

import com.example.springsecurityredis.repository.CompanyEntityRepository;
import com.example.springsecurityredis.repository.MemberEntityRepository;
import com.example.springsecurityredis.repository.RoleEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.example.springsecurityredis.config.security.entity.RoleEntity.SecurityRoles.*;

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

    @Test
    @Transactional
    @Commit
    public void test1(){
    }

//    @Test
//    @Transactional
//    @Commit
//    public void test2() throws Exception {
//
//        CompanyEntity companyA = companyEntityRepository.findByCompanyName("companyA").orElseThrow(() -> new Exception("그딴 회사 없다"));
//        CompanyEntity companyB = companyEntityRepository.findByCompanyName("companyB").orElseThrow(() -> new Exception("그딴 회사 없다"));
//
//        MemberEntity admin = new MemberEntity();
//        admin.addRole(ADMIN);
//        admin.setUsername("admin");
//        admin.attachCompany(companyA);
//        admin.attachCompany(companyA);
//        admin.attachCompany(companyB);
//
//
//        MemberEntity user = new MemberEntity();
//        user.setUsername("user");
//        user.addRole(USER);
//        user.addRole(USER);
//        user.removeRole(ADMIN);
//        user.attachCompany(companyB);
//
//        memberEntityRepository.save(admin);
//        memberEntityRepository.save(user);
//
//        em.flush();
//        em.clear();
//
//        Optional<MemberEntity> findAdmin = memberEntityRepository.findByUsername("admin");
//        findAdmin.get().addRole(USER);
//
//
//    }
}