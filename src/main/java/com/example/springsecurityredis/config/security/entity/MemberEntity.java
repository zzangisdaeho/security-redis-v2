package com.example.springsecurityredis.config.security.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Slf4j
@ToString(exclude = "companyMembers")
public class MemberEntity {

    @Id
    @GeneratedValue
    private long memberSeq;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CompanyMemberR> companyMembers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MEMBER_ROLE_R",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    private void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    private void preUpdate(){
        updatedDate = LocalDateTime.now();
    }

    public void attachCompany(CompanyEntity company){
        long count = companyMembers.stream().filter(e -> ((e.getCompany().getCompanySeq() == company.getCompanySeq()) && (e.getMember().getMemberSeq() == this.memberSeq))).count();
        if(count > 0){
            log.debug("=======================이미 소속된 맴버입니다");
        }else{
            CompanyMemberR companyMemberR = CompanyMemberR.builder().member(this).company(company).build();
            companyMembers.add(companyMemberR);
            company.registMember(this);
        }

    }

    public void detachCompany(CompanyEntity company){
        long count = companyMembers.stream().filter(e -> ((e.getCompany().getCompanySeq() == company.getCompanySeq()) && (e.getMember().getMemberSeq() == this.memberSeq))).count();
        if(count < 1){
            log.debug("==========================소속되지 않은 맴버입니다");
        }else{
            companyMembers.remove(company);
        }
    }


    public void addRole(SecurityRoles role){
        if(this.roles.stream().filter(e -> e.getSecurityRoles() == role).count() == 0){
            this.roles.add(new RoleEntity(role));
        }else{
            log.debug("role 중복 들어오기에 처리안함");
        }
    }

    public void removeRole(SecurityRoles role){
        if(this.roles.stream().filter(e -> e.getSecurityRoles() == role).count() != 0){
            this.roles.remove(new RoleEntity(role));
        }else{
            log.debug("존재하지 않는 role 삭제 시도하여 처리안함");
        }
    }

}
