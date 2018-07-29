package com.au.siteminder.service;

import com.au.siteminder.framework.client.EmailClient;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailClient emailClient;

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        return emailClient.sendEmail(emailRequest);
    }


}
