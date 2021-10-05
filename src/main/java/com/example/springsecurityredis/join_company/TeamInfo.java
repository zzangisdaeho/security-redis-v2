package com.example.springsecurityredis.join_company;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class TeamInfo {

    private long teamSeq;
    private String teamName;
}
