package com.example.springsecurityredis.config.security.entity;

import com.example.springsecurityredis.config.security.entity.RoleEntity.SecurityRoles;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Slf4j
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ"
)
@EqualsAndHashCode(of = "memberSeq")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private int memberSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companySeq")
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq")
    private UserEntity user;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<TeamMemberRelationEntity> teamMemberRelations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MEMBER_ROLE_RELATION_ENTITY",
            joinColumns = @JoinColumn(name = "MEMBER_SEQ"),
            inverseJoinColumns = @JoinColumn(name = "ROLE")
    )
    @Builder.Default
    private Set<RoleEntity> roles = new HashSet<>();

    public void addRole(SecurityRoles role){
        System.out.println("role ? = " + roles);
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
