package com.example.springsecurityredis.config.security;

import com.example.springsecurityredis.config.security.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 김대호
 * Spring Security를 위한 기본 설정 클래스
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final CustomUserDetailsService customUserDetailsService;

    private final AuthenticationFilter authenticationFilter;

    /**
     * 김대호
     * security passwordEncoder bean 등록
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 김대호
     * security의 http설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http 요청 보안
        http.authorizeRequests()
                .antMatchers("/code", "/h2-console/**", "/common/**", "/main").permitAll()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/**/**").authenticated()
                .antMatchers("/**/admin/**").hasAnyRole("ADMIN")
                .anyRequest().permitAll();

        http.csrf().disable();

        //로그인 설정
//        http.formLogin()
//            .loginPage("/login")
//            .permitAll()
//        ;

        //로그아웃 설정
        http.logout()
                .logoutSuccessUrl("/bye");

        //권한 없는 사용자 처리
        http.exceptionHandling()
                .accessDeniedPage("/denied");

        //h2 console을 출력하기 위한 옵션
        http.headers().frameOptions().disable();

        //TwoFactorAuthenticationFilter 등록
        http.addFilter(generateCustomAuthenticationFilter())
//            .addFilterBefore(authenticationFilter, LogoutFilter.class)
            .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 김대호
     * CustomUserDetailService를 사용하도록 설정
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 김대호
     * TwoFactorAuthenticationFilter에 AuthenticationManaber를 Set하여 Filter에 제공하기 위한 메서드
     * @return
     * @throws Exception
     */
    private TwoFactorAuthenticationFilter generateCustomAuthenticationFilter() throws Exception {
        TwoFactorAuthenticationFilter twoFactorAuthenticationFilter = new TwoFactorAuthenticationFilter();
        twoFactorAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return twoFactorAuthenticationFilter;
    }
}
