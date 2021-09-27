package com.example.springsecurityredis.config.security.dto;

import com.example.springsecurityredis.config.security.entity.CompanyEntity;
import lombok.Data;

import java.util.List;

/**
 * 김대호
 * Login시 Security Session 진입 전, Google User info를 통해 해당 유저의 CompanyList를 선택하도록 하기 위한 DTO
 */
@Data
public class LoginInfoDto {

    private final long userSeq;
    private final String username;

    private final List<CompanyListDto> companyList;


    @Data
    public static class CompanyListDto {

        private long companySeq;
        private String companyName;

        public CompanyListDto(CompanyEntity companyEntity){
            this.companySeq = companyEntity.getCompanySeq();
            this.companyName = companyEntity.getCompanyName();
        }
    }
}
