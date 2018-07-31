package com.au.siteminder.framework.handler;

import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;

public abstract class EmailHandler {

    protected EmailHandler next;

    public void setNext(EmailHandler handler) {
        next = handler;
    }

    public abstract EmailResponse sendEmail(EmailRequest emailRequest);

}