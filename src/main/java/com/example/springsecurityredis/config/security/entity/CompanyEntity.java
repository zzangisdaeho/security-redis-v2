package com.example.springsecurityredis.config.security.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name = "COMPANY_SEQ_GENERATOR",
        sequenceName = "COMPANY_SEQ"
)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "companySeq")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ_GENERATOR")
    private long companySeq;

    @Column(unique = true)
    private String companyName;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberEntity> members = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TeamEntity> teams = new ArrayList<>();

    public MemberEntity registerMember(UserEntity userEntity) {
        MemberEntity memberEntity = MemberEntity.builder()
                .user(userEntity)
                .company(this)
                .build();
        this.members.add(memberEntity);

        return memberEntity;
    }

    //조직 생성시 최상위 팀 등록할때 사용 (1회)
    public TeamEntity registerTeam(String teamName){
        TeamEntity team = TeamEntity.builder()
                .teamName(teamName)
                .company(this)
                .build();
        this.teams.add(team);

        return team;
    }
}
