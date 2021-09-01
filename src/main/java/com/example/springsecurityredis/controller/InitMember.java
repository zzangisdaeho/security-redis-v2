package com.example.springsecurityredis.controller;

import com.example.springsecurityredis.config.security.entity.*;
import com.example.springsecurityredis.repository.CompanyEntityRepository;
import com.example.springsecurityredis.repository.MemberEntityRepository;
import com.example.springsecurityredis.repository.RoleEntityRepository;
import com.example.springsecurityredis.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;

import static com.example.springsecurityredis.config.security.entity.RoleEntity.SecurityRoles.*;

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

        private final UserEntityRepository userEntityRepository;


        @PersistenceContext
        private EntityManager em;

        /*
        * 로직 흐름
        * 1. 사전에 정의된 ROLE이 등록된다.
        * 2. 유저가 가입한다.
        * 3. 조직이 만들어진다.
        * 4. 조직에 유저가 맴버로 참여한다.
        * 5. 맴버로 참여한 유저에게 ROLE을 부여한다.
        * 6. 조직에 팀을 만든다.
        * 7. 팀에 맴버가 가입한다.
        * */
        @Transactional
        public void init(){
            // 1. 사전에 정의된 ROLE 등록
            RoleEntity role_admin = roleEntityRepository.save(new RoleEntity(ADMIN));
            RoleEntity role_user = roleEntityRepository.save(new RoleEntity(USER));

            // 2. 유저가 가입한다.
            UserEntity user1 = UserEntity.builder()
                    .username("user1")
                    .password(passwordEncoder.encode("1234"))
                    .build();
            UserEntity user2 = UserEntity.builder()
                    .username("user2")
                    .password(passwordEncoder.encode("1234"))
                    .build();

            userEntityRepository.save(user1);
            userEntityRepository.save(user2);

            // 3. 조직이 만들어진다.
            CompanyEntity companyA = CompanyEntity.builder().companyName("companyA").build();
            CompanyEntity companyB = CompanyEntity.builder().companyName("companyB").build();

            companyEntityRepository.save(companyA);
            companyEntityRepository.save(companyB);

            // 4. 조직에 유저가 맴버로 참여한다.
            MemberEntity companyA_user1 = companyA.registerMember(user1);
            MemberEntity companyA_user2 = companyA.registerMember(user2);

            MemberEntity companyB_user1 = companyB.registerMember(user1);
            MemberEntity companyB_user2 = companyB.registerMember(user2);

            // 5. 맴버로 참여한 유저에게 ROLE을 부여한다.
            companyA_user1.addRole(USER);
            companyA_user1.addRole(ADMIN);
            companyA_user2.addRole(ADMIN);

            companyB_user1.addRole(USER);
            companyB_user1.addRole(ADMIN);
            companyB_user2.addRole(ADMIN);

            // 6. 조직에 팀을 만든다.
            TeamEntity teamA = companyA.registerTeam("teamA");

            TeamEntity teamA_1 = TeamEntity.builder()
                    .teamName("teamA_1")
                    .build();
            TeamEntity teamA_2 = TeamEntity.builder()
                    .teamName("teamA_2")
                    .build();
            TeamEntity teamA_1_1 = TeamEntity.builder()
                    .teamName("teamA_1_1")
                    .build();

            teamA.addSubTeam(teamA_1);
            teamA_1.addSubTeam(teamA_1_1);
            teamA.addSubTeam(teamA_2);

            TeamEntity teamB = companyB.registerTeam("teamB");

            TeamEntity teamB_1 = TeamEntity.builder()
                    .teamName("teamB_1")
                    .build();

            teamB.addSubTeam(teamB_1);

            // 7. 팀에 맴버를 추가
            teamA.addTeamMember(companyA_user1, true);
            teamA_1_1.addTeamMember(companyA_user1, true);
            teamA_1_1.addTeamMember(companyA_user2, false);


        }
    }
}