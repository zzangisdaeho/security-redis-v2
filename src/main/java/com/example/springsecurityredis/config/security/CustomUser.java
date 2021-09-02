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
    }

    @ToString
    public class WhoAreYou implements Serializable {

        private long memberSeq;

        private long userSeq;

        private long companySeq;

        private List<Long> teamsSeq;
    }
}
