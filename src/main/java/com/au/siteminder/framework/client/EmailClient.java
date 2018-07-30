package com.au.siteminder.framework.client;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.framework.handler.EmailHandler;
import com.au.siteminder.framework.handler.MailgunEmailHandler;
import com.au.siteminder.framework.handler.SendGridEmailHandler;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EmailClient {

    private EmailHandler emailHandler;

    @Autowired
    private MailgunEmailHandler mailgunEmailHandler;

    @Autowired
    private SendGridEmailHandler sendGridEmailHandler;

    @PostConstruct
    public void init() {
        emailHandler = mailgunEmailHandler;
        emailHandler.setNext(sendGridEmailHandler);
    }

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        return emailHandler.sendEmail(emailRequest);
    }

}
