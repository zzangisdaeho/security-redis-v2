package com.example.springsecurityredis.config.security.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Slf4j
@SequenceGenerator(
        name = "TEAM_MEMBER_SEQ_GENERATOR",
        sequenceName = "TEAM_MEMBER_SEQ"
)
public class TeamMemberRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_MEMBER_SEQ_GENERATOR")
    private Long teamMemberSeq;

    private boolean leader;

    @ManyToOne
    @JoinColumn(name = "teamSeq")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "memberSeq")
    private MemberEntity member;


}
