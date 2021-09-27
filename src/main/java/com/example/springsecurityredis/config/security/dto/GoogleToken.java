package com.example.springsecurityredis.config.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 김대호
 * Google Token info DTO class
 */
@Getter
@Setter
@ToString
public class GoogleToken {
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
    private String id_token;
}
