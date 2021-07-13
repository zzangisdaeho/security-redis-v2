package com.example.springsecurityredis.config.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CompanyEntity {

    @Id
    @GeneratedValue
    private long companySeq;

    @Column(unique = true)
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<CompanyMemberR> companyMembers = new ArrayList<>();


    public void registMember(MemberEntity memberEntity) {
        CompanyMemberR companyMemberR = CompanyMemberR.builder().member(memberEntity).company(this).build();
        this.companyMembers.add(companyMemberR);
    }
}
