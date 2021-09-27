package com.example.springsecurityredis.service;

import com.example.springsecurityredis.config.security.entity.TokenInfoEntity;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * 김대호
 * Google Credential 제작 담당 Service
 */
@Service
@RequiredArgsConstructor
public class GoogleCredentialService {

    private final JSONObject clientSecret;

    private final JsonFactory googleJsonFactory;

    private final TokenService tokenService;

    /**
     * 김대호
     * Google API call을 위한 Credential generator
     * @param userSeq
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public GoogleCredential generateCredential(long userSeq) throws GeneralSecurityException, IOException {
        TokenInfoEntity token = tokenService.getToken(userSeq);

        return new GoogleCredential.Builder()
                .setClientSecrets(clientSecret.getString("client_id"), clientSecret.getString("client_secret"))
                .setJsonFactory(googleJsonFactory).setTransport(GoogleNetHttpTransport.newTrustedTransport()).build()
                .setAccessToken(token.getAccessToken()).setRefreshToken(token.getRefreshToken());
    }
}
