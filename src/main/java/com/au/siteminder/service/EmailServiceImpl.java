package com.au.siteminder.service;

import com.au.siteminder.framework.FailoverRestTemplate;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment environment;

    @Autowired
    private FailoverRestTemplate failoverRestTemplate;

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        EmailResponse response = new EmailResponse();
        response.setId(environment.getProperty("MAILGUN_USER"));
        response.setMessage(environment.getProperty("MAILGUN_KEY"));
        return response;
    }

}
