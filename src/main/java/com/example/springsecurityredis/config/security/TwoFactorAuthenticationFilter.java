package com.example.springsecurityredis.config.security;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 김대호
 * CustomUserDetailsService 에서 Username 단일 파라미터만 받을 수 있기에 이를 가공해주는 Filter
 * 해당 Filter를 통해서 username, companySeq로 받은 데이터를 Username:CompanySeq 형식으로 convert 하여 CustomUserDetailsService에 전달한다.
 */
@Getter
public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private String originParameter = "username";
    private String extraParameter = "companySeq";
    private String delimiter = ":";


    /**
     * Given an {@link HttpServletRequest}, this method extracts the username and the extra input
     * values and returns a combined username string of those values separated by the delimiter
     * string.
     *
     * @param request The {@link HttpServletRequest} containing the HTTP request variables from
     *   which the username client domain values can be extracted
     */
    // login시 username에 : 을 붙여서 컴페니 seq를 같이 넘겨받기 위한 설정
    @Override
    protected String obtainUsername(HttpServletRequest request)
    {
        String username = request.getParameter(originParameter);
        String extraInput = request.getParameter(extraParameter);

        String combinedUsername = username + delimiter + extraInput;

        System.out.println("Combined username = " + combinedUsername);
        return combinedUsername;
    }
}