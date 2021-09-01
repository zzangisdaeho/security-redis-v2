package com.example.springsecurityredis.config.security.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Slf4j
@SequenceGenerator(
        name = "TEAM_SEQ_GENERATOR",
        sequenceName = "TEAM_SEQ"
)
@EqualsAndHashCode(of = "teamSeq")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GENERATOR")
    private long teamSeq;

    private String teamName;

    @ManyToOne
    @JoinColumn(name = "companySeq")
    private CompanyEntity company;

    @OneToMany(mappedBy = "upperTeam", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<TeamEntity> lowerTeam = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "upperTeamSeq")
    private TeamEntity upperTeam;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TeamMemberRelationEntity> teamMemberRelationEntities = new ArrayList<>();

    public void addSubTeam(TeamEntity team){
        team.company = this.company;
        team.upperTeam = this;
        lowerTeam.add(team);
    }

    public void removeSubTeam(TeamEntity team){
        lowerTeam.remove(team);
    }

    public void moveThisTeamTo(TeamEntity NewUpperTeam, TeamEntity oldUpperTeam){
        NewUpperTeam.addSubTeam(this);
        oldUpperTeam.removeSubTeam(this);
    }

    public void addTeamMember(MemberEntity memberEntity, boolean isLeader){
        TeamMemberRelationEntity teamMemberRelationEntity = TeamMemberRelationEntity
                .builder()
                .member(memberEntity)
                .team(this)
                .leader(isLeader)
                .build();
        this.teamMemberRelationEntities.add(teamMemberRelationEntity);
    }

    public void removeTeamMember(MemberEntity memberEntity){
        this.teamMemberRelationEntities.remove(memberEntity);
    }
}
