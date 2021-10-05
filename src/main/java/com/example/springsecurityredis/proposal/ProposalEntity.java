package com.example.springsecurityredis.proposal;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

/**
 * 김대호
 * 제안서 저장용 테이블, 추후 Audit 등록 필요
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Slf4j
@SequenceGenerator(
        name = "PROPOSAL_SEQ_GENERATOR",
        sequenceName = "PROPOSAL_SEQ"
)
@EqualsAndHashCode(of = "proposalSeq")
public class ProposalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROPOSAL_SEQ_GENERATOR")
    private Long proposalSeq;

    @NotEmpty(message = "회사명은 필수값입니다.")
    @Max(value = 30, message = "회사명은 30자 이내여야 합니다")
    private String companyName;

    private String companyAddress;

    @NotEmpty(message = "임직원수는 필수값입니다.")
    @PositiveOrZero(message = "임직원수는 0명 미만일 수 없습니다.")
    private int totalEmployeeCount;

    @NotEmpty(message = "담당자명은 필수값입니다")
    private String chargerName;

    @NotEmpty(message = "담당자이메일은 필수값입니다")
    @Email(message = "옳지 않은 이메일 형식입니다")
    private String chargerEmail;

    @NotEmpty(message = "담당자 연락처는 필수값입니다")
    private String contactPhoneNumber;

    @NotEmpty(message = "가입경로는 필수값입니다")
    private String subscriptionPath;

    @NotEmpty(message = "약관동의는 필수값입니다")
    private boolean accept;
}
