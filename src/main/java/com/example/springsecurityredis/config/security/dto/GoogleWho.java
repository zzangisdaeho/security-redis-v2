package com.example.springsecurityredis.config.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 김대호
 * Google User info DTO
 */
@Getter
@Setter
@ToString
public class GoogleWho {

    private String id;
    private String email;
    private String name;
    private String locale;
    private String hd;
    private String picture;
}
