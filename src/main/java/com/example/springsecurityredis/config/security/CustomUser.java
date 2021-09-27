package com.example.springsecurityredis.config.security;

import com.example.springsecurityredis.config.security.entity.MemberEntity;
import com.example.springsecurityredis.config.security.entity.TeamEntity;
import com.example.springsecurityredis.config.security.entity.TeamMemberRelationEntity;
import com.example.springsecurityredis.config.security.entity.UserEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 김대호
 * Security User를 가공한 클래스
 * Gcp Redis Session과 연동되어있다.
 * 유저 특정성을 위해 필요한 정보 (userSeq, memberSeq, companySeq, teamsSeq, roles)를 추가로 담는다.
 * WhoAreYou class는 추후 Custom Annotation을 통해 유저를 손쉽게 특정하기 위해 inner class로 제작
 */
@Getter
@ToString
public class CustomUser extends User {

    private WhoAreYou who = new WhoAreYou();

    public CustomUser(UserEntity userEntity, MemberEntity memberEntity, Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getUsername(), userEntity.getPassword(), authorities);

        this.who.memberSeq = memberEntity.getMemberSeq();
        this.who.userSeq = userEntity.getUserSeq();
        this.who.companySeq = memberEntity.getCompany().getCompanySeq();
        this.who.teamsSeq = memberEntity.getTeamMemberRelations().stream().map(TeamMemberRelationEntity::getTeam).map(TeamEntity::getTeamSeq).collect(Collectors.toList());
        this.who.roles = authorities;
    }

    @ToString
    @Getter
    public class WhoAreYou implements Serializable {

        private long memberSeq;

        private long userSeq;

        private long companySeq;

        private List<Long> teamsSeq;

        private Collection<? extends GrantedAuthority> roles;

    }
}
