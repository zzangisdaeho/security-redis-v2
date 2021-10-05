package com.example.springsecurityredis.config.security;

import com.example.springsecurityredis.config.security.entity.MemberEntity;
import com.example.springsecurityredis.config.security.entity.RoleEntity;
import com.example.springsecurityredis.config.security.entity.UserEntity;
import com.example.springsecurityredis.repository.MemberEntityRepository;
import com.example.springsecurityredis.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 김대호
 * Spring Security Login Process를 위한 UserDetailsService 구현체
 * username:companySeq 형식으로 정보를 받아 유저를 특정하여 session 생성한다.
 * GCP redis와 연동되어있음.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberEntityRepository memberEntityRepository;

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {

        //username:companySeq 로 넘어온 id값을 분리해서 식별하기 위함
        String[] split = input.split(":");
        String username = split[0];
        long companySeq = Long.parseLong(split[1]);

        UserEntity findUser = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다"));

        //companySeq 유저가 소속되어있는 맴버를 가져온다. 맴버가 회사에 갖고있는 role리스트를 가져온다.
        MemberEntity findMember = memberEntityRepository.findByUserUserSeqAndCompanyCompanySeq(findUser.getUserSeq(), companySeq)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 맴버 정보입니다."));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (RoleEntity role : findMember.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getSecurityRoles().name()));
        }

        return new CustomUser(findUser, findMember, grantedAuthorities);
    }
}
