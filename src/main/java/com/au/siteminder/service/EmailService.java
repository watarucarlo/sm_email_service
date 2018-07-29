package com.au.siteminder.service;

import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;

public interface EmailService {

    EmailResponse sendEmail(EmailRequest emailRequest);
    
}
