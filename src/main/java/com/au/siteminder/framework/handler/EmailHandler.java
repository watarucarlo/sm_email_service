package com.au.siteminder.framework.handler;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;

public abstract class EmailHandler {

    @Getter
    @Setter
    protected EnvironmentProperty environmentProperty;

    @Getter
    @Setter
    protected RestTemplateBuilder restTemplateBuilder;

    protected EmailHandler next;

    public void setNext(EmailHandler handler) {
        next = handler;
    }

    public abstract EmailResponse sendEmail(EmailRequest emailRequest);

}