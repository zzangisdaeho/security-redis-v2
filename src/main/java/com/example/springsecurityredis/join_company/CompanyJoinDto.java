package com.example.springsecurityredis.join_company;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CompanyJoinDto {

    private String name;
    private String email;
    private List<Long> teams;
    private Date entryDate;
    private String employeeNo;
    private String position;
    private String duty;
    private long companySeq;

}
