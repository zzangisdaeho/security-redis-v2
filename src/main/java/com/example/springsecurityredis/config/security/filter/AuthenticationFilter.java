package com.example.springsecurityredis.config.security.filter;

import com.example.springsecurityredis.config.security.CustomUser;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

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

        filterChain.doFilter(request, response);

    }

}
