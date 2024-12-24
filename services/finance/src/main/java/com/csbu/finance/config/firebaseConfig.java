package com.csbu.finance.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class firebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        String serviceAccountPath = System.getProperty("user.dir")+"/services/finance/src/main/java/com/csbu/finance/config/spring-boot-firebase.json";
        FileInputStream serviceAccountStream = new FileInputStream(serviceAccountPath);

        FirebaseOptions options= FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setStorageBucket("bcu-study-spaces.appspot.com")
                .build();
        return FirebaseApp.initializeApp(options);
    }

}
