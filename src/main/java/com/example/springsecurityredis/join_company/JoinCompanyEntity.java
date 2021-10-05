package com.example.springsecurityredis.join_company;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Slf4j
@SequenceGenerator(
        name = "JOIN_COMPANY_SEQ_GENERATOR",
        sequenceName = "JOIN_COMPANY_SEQ"
)
@EqualsAndHashCode(of = "joinCompanySeq")
@ToString
public class JoinCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOIN_COMPANY_SEQ_GENERATOR")
    private Long joinCompanySeq;

    private String name;
    private String email;

    @ElementCollection
    @CollectionTable(
            name = "joinCompanyTeams",
            joinColumns = @JoinColumn(name = "joinCompanySeq")
    )
//    @OrderColumn(name = "teams_idx") // index 순서 상관 없음
//    @Column(name = "teamName") // 값타입이 객체로써 컬럼명 별도 지정 안해도 됨.
    private List<TeamInfo> teams;

    private LocalDate entryDate;
    private String employeeNo;
    private String position;
    private String duty;
    private long companySeq;

}
