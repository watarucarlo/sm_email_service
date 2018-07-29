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

@Component
public class EmailClient {

    @Getter
    @Setter
    private EmailHandler emailHandler;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private EnvironmentProperty environmentProperty;

    public EmailClient(EnvironmentProperty environmentProperty, RestTemplateBuilder restTemplateBuilder) {
        emailHandler = new MailgunEmailHandler(environmentProperty, restTemplateBuilder);
        EmailHandler failoverHandler = new SendGridEmailHandler(environmentProperty, restTemplateBuilder);
        emailHandler.setNext(failoverHandler);
    }

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        return emailHandler.sendEmail(emailRequest);
    }

}
