package com.example.springsecurityredis.config.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CompanyMemberRPK.class)
public class CompanyMemberR {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companySeq")
    private CompanyEntity company;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberSeq")
    private MemberEntity member;
}
