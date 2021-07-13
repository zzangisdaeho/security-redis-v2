package com.example.springsecurityredis.config.security.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
public class CompanyMemberRPK implements Serializable {

    private CompanyEntity company;
    private MemberEntity member;
}
