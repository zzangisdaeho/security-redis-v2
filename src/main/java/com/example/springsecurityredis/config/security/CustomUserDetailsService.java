package com.example.springsecurityredis.config.security;

import com.example.springsecurityredis.config.security.entity.MemberEntity;
import com.example.springsecurityredis.config.security.entity.RoleEntity;
import com.example.springsecurityredis.repository.MemberEntityRepository;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberEntityRepository memberEntityRepository;

    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity findMember = memberEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 멤버입니다"));

        //member_seq를 통해 해당 맴버가 해당 회사에 갖고있는 role리스트 가져와서 grantedAuthorities에 넣어주기

        request.getParameter("company");

       Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (RoleEntity role : findMember.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getSecurityRoles().name()));
        }

        return new CustomUser(findMember, grantedAuthorities);
    }
}
