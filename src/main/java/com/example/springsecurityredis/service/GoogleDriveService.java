package com.example.springsecurityredis.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * 김대호
 * Google Drive API 연동을 위한 Service
 */
@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final JsonFactory jsonFactory;

    private final GoogleCredentialService googleCredentialService;

    /**
     * 김대호
     * Google Drive API 연동을 위한 Drive object 생성 메서드
     * @param userSeq
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Drive getDrive(long userSeq) throws Exception {
        GoogleCredential credential = googleCredentialService.generateCredential(userSeq);

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Drive service = new Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("DOCSWAVE V3")
                .build();
        return service;
    }

    public void getDriveListSample(long userSeq) throws Exception {
        Drive service = getDrive(userSeq);

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }
}
