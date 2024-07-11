package com.yanchware.gcp;

import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GcpConfig {

    @Value("classpath:gcp-service-account.json")
    Resource credentialsJson;

    @Bean
    public Compute compute() throws IOException, GeneralSecurityException {

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(credentialsJson.getInputStream())
                .createScoped(ComputeScopes.all());

        return new Compute.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("GCP Service Broker")
                .build();
    }
}
