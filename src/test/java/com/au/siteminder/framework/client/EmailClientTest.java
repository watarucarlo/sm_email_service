package com.au.siteminder.framework.client;

import com.au.siteminder.framework.exception.SiteminderServicesException;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class EmailClientTest {

    @Mock
    private MailgunEmailHandler mailgunEmailHandler;

    @Mock
    private SendGridEmailHandler sendGridEmailHandler;

    @InjectMocks
    private EmailClient emailClient = new EmailClient();

    private EmailRequest emailRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        emailClient.init();
        emailRequest = EmailRequestMother.build();
    }

    @Test
    public void whenSendEmailSucceeds() {
        when(mailgunEmailHandler.sendEmail(emailRequest)).thenReturn(EmailResponseMother.build());
        EmailResponse response = emailClient.sendEmail(emailRequest);
        assertNotNull(response);
        assertEquals(EmailResponseMother.STATUS, response.getStatus());
        assertEquals(EmailResponseMother.PROVIDER, response.getProvider());
    }

    @Test(expected = SiteminderServicesException.class)
    public void whenSendEmailFails() {
        when(mailgunEmailHandler.sendEmail(emailRequest)).thenThrow(SiteminderServicesException.class);
        emailClient.sendEmail(emailRequest);
    }

}
