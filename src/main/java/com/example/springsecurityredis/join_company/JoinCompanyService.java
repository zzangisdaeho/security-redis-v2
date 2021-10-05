package com.example.springsecurityredis.join_company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinCompanyService {

    private final JoinCompanyEntityRepository joinCompanyEntityRepository;

    @Transactional
    public JoinCompanyEntity joinCompany(JoinCompanyEntity joinCompanyEntity) {
        return joinCompanyEntityRepository.save(joinCompanyEntity);
    }
}
