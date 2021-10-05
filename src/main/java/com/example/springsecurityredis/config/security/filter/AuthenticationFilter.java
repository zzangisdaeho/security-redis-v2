package com.example.springsecurityredis.config.security.filter;

import com.example.springsecurityredis.config.security.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * 김대호
 * Request에 대한 요청 정보 및, Security Context를 확인하기 위한 Filter
 * UsernamePassword AuthenticationFilter 다음단에 배치
 */
@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final FilterService filterService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        System.out.println("================ AuthenticationFilter Start ===================");

        String requestUri = request.getRequestURI();
        System.out.println("requestUri = " + requestUri);

        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println("context : " + context);

        Authentication authentication = context.getAuthentication();
        System.out.println("authentication : " + authentication);

        Object principal = null;
        if(authentication != null){
            principal = authentication.getPrincipal();
        }
        System.out.println("principal : " + principal);

        if(principal != null){

            // 회사 도메인을 가져옴. 도메인이 없을시 empty
            String companyDomain = null;
            try{
                companyDomain = requestUri.substring(1, requestUri.indexOf("/", 1));
            }catch (StringIndexOutOfBoundsException e){
                companyDomain = requestUri.substring(1);
            }
            System.out.println("companyDomain : " + companyDomain);

            System.out.println("================ AuthenticationFilter End===================");

            // companyDomain 이 case에 걸리지 않을시 인증 로직 진입
            // CompanySeq와 principal 내 companySeq가 동일하지 않을 시 조직맴버가 아니라고 판단하여 error response
            try{
                switch (companyDomain){
                    case "" : case "code": case "login": case "logout": case "common": case "user": case "main": case "h2-console"
                            : filterChain.doFilter(request, response); break;
                    default:
                        if(isMember(companyDomain, principal)){
                            filterChain.doFilter(request, response);
                        }else{
                            throw new Exception("해당 회사의 맴버가 아닙니다.");
                        }
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
                response.setStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("AuthenticationFilter error : 해당 회사의 맴버가 아닙니다.");
            }
        }else{
            filterChain.doFilter(request, response);
        }

    }

    private boolean isMember(String companyDomain, Object principal) throws Exception {
        long companySeqByDomain = filterService.getCompanySeqByDomain(companyDomain);

        return ((CustomUser) principal).getWho().getCompanySeq() == companySeqByDomain;
    }

}
