package com.au.siteminder.controller;


import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailRequestMother;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.model.EmailResponseMother;
import com.au.siteminder.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


public class EmailControllerTest {

    @Mock
    private EmailService emailService;

    private EmailRequest emailRequest;

    @InjectMocks
    private EmailController emailController = new EmailController();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        emailRequest = EmailRequestMother.build();
        when(emailService.sendEmail(emailRequest)).thenReturn(EmailResponseMother.build());
    }

    @Test
    public void whenSuccessfulMailSent() {
        EmailResponse emailResponse = emailController.sendEmail(emailRequest);
        assertNotNull(emailResponse);
        assertEquals(EmailResponseMother.STATUS, emailResponse.getStatus());
        assertEquals(EmailResponseMother.PROVIDER, emailResponse.getProvider());
    }

    @Test(expected = SiteminderServicesException.class)
    public void whenMessageSendingFailed() {
        when(emailService.sendEmail(emailRequest)).thenThrow(new SiteminderServicesException());
        emailController.sendEmail(emailRequest);
    }

}
