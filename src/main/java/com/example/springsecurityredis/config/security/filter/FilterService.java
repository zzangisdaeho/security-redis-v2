package com.example.springsecurityredis.config.security.filter;

import com.example.springsecurityredis.repository.CompanyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 김대호
 */
@Service
@RequiredArgsConstructor
public class FilterService {

    private final CompanyEntityRepository companyEntityRepository;

    /**
     * 로그인 이후 요청 URL에 대해 compnaySeq를 비교하기 위해 시퀀스값을 리턴하는 기능
     * 자주 요청되는 값으로 caching
     * @param domain
     * @return
     * @throws Exception
     */
    @Cacheable(value = "companySeqByDomain", key = "#domain")
    @Transactional(readOnly = true)
    public long getCompanySeqByDomain(String domain) throws Exception {

        System.out.println("========cacheable called=========");

        long companySeq = companyEntityRepository.findByDomain(domain).orElseThrow(() -> new Exception("도메인에 해당하는 회사가 없습니다.")).getCompanySeq();

        return companySeq;
    }
}
