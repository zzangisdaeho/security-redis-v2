package com.example.springsecurityredis.config.security.dto;

import com.example.springsecurityredis.config.security.entity.CompanyEntity;
import lombok.Data;

import java.util.List;

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
