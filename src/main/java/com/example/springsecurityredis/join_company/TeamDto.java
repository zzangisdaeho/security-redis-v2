package com.example.springsecurityredis.join_company;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamDto {

    private long teamSeq;
    private String teamName;
//    private long upperTeamSeq;
    private List<TeamDto> lowerTeam;
}
