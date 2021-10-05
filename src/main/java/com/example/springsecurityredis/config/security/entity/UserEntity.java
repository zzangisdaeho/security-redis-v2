package com.example.springsecurityredis.config.security.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 김대호
 * Audit 등록 필요
 */
@Entity
@Getter
@Slf4j
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ"
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "userSeq")
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private long userSeq;

    @Column(unique = true)
    private String username;
    private String password;

    private String mail;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberEntity> members = new ArrayList<>();

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
        long count = company.getMembers().stream().filter(e -> ((e.getCompany().getCompanySeq() == company.getCompanySeq()) && (e.getUser().getUserSeq() == this.userSeq))).count();
        if(count > 0){
            log.debug("=======================이미 소속된 유저입니다");
        }else{
            MemberEntity member = MemberEntity.builder().user(this).company(company).build();
            members.add(member);
        }

    }

    public void detachCompany(CompanyEntity company){
        long count = company.getMembers().stream().filter(e -> ((e.getCompany().getCompanySeq() == company.getCompanySeq()) && (e.getUser().getUserSeq() == this.userSeq))).count();
        if(count < 1){
            log.debug("==========================소속되지 않은 맴버입니다");
        }else{
            members.remove(company);
        }
    }

    public List<CompanyEntity> getCompanyList(){
        return this.members.stream().map( i -> i.getCompany() ).collect(Collectors.toList());
    }

}
