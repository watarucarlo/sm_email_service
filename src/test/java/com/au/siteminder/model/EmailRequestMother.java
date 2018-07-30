package com.au.siteminder.model;

import com.au.siteminder.model.EmailRequest;

import java.util.Collections;

public class EmailRequestMother {

    public static EmailRequest build() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setFrom("test@test.com");
        emailRequest.setSubject("test");
        emailRequest.setText("test");
        emailRequest.setTo(Collections.singletonList("test1@test.com"));
        emailRequest.setCc(Collections.singletonList("test2@test.com"));
        emailRequest.setBcc(Collections.singletonList("test3@test.com"));
        return emailRequest;
    }
}
