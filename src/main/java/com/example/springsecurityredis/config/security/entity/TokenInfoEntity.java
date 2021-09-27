package com.example.springsecurityredis.config.security.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TokenInfoEntity implements Serializable {

    private static final long serialVersionUID = 7266748623215092241L;

    @Id
    private long userSeq;

    private String accessToken;

    private String refreshToken;
}
