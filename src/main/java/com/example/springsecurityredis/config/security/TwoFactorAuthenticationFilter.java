package com.example.springsecurityredis.config.security;

import lombok.Getter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
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
        String username = request.getParameter(extraParameter);
        String extraInput = request.getParameter(delimiter);

        String combinedUsername = username + delimiter + extraInput;

        System.out.println("Combined username = " + combinedUsername);
        return combinedUsername;
    }
}