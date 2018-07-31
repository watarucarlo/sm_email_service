package com.au.siteminder.service;

import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;

/**
 * Service used for sending emails
 *
 */
public interface EmailService {

    /**
     *
     *  Main method used for sending plain text emails
     *
     * @param emailRequest
     * @return emailResponse
     */
    EmailResponse sendEmail(EmailRequest emailRequest);
    
}
