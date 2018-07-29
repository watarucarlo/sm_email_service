package com.au.siteminder.framework.client;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.framework.handler.EmailHandler;
import com.au.siteminder.framework.handler.MailgunEmailHandler;
import com.au.siteminder.framework.handler.SendGridEmailHandler;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailRequestMother;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.model.EmailResponseMother;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class EmailClientTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private EnvironmentProperty environmentProperty;

    @Mock
    private EmailHandler emailHandler;

    @Mock
    private EmailHandler failoverHandler;

    @InjectMocks
    private EmailClient emailClient;

    private EmailRequest emailRequest;

    @Before
    public void setup() {
        emailHandler = new MailgunEmailHandler(environmentProperty, restTemplateBuilder);
        failoverHandler = new SendGridEmailHandler(environmentProperty, restTemplateBuilder);
        MockitoAnnotations.initMocks(this);
        emailClient.setEmailHandler(emailHandler);
        emailRequest = EmailRequestMother.build();
    }

    @Test
    public void whenSendEmailSucceeds() {
        when(emailHandler.sendEmail(emailRequest)).thenReturn(EmailResponseMother.build());
        EmailResponse response = emailClient.sendEmail(emailRequest);
        assertNotNull(response);
        assertEquals(EmailResponseMother.STATUS, response.getStatus());
        assertEquals(EmailResponseMother.PROVIDER, response.getProvider());
    }

    @Test(expected = SiteminderServicesException.class)
    public void whenSendEmailFails() {
        when(emailHandler.sendEmail(emailRequest)).thenThrow(SiteminderServicesException.class);
        emailClient.sendEmail(emailRequest);
    }

    @Test
    public void testFailover(){
        emailHandler.setNext(failoverHandler);
        when(failoverHandler.sendEmail(emailRequest)).thenReturn(EmailResponseMother.build());
        emailClient.sendEmail(emailRequest);
    }

}
