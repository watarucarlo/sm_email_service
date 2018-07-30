package com.au.siteminder.framework.handler;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailRequestMother;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.model.common.StatusEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class SendGridEmailHandlerTest {

    @Mock
    private EnvironmentProperty environmentProperty;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SendGridEmailHandler sendGridEmailHandler = new SendGridEmailHandler();

    @InjectMocks
    private MailgunEmailHandler mailgunEmailHandler = new MailgunEmailHandler();

    private EmailRequest emailRequest;

    private String MAILGUN_URI = "https://mailgun";
    private String SENDGRID_URI = "https://sendgrid";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        emailRequest = EmailRequestMother.build();
    }

    @Test
    public void testSendMail() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(environmentProperty.getSendGridURI()).thenReturn(SENDGRID_URI);
        EmailResponse response = sendGridEmailHandler.sendEmail(emailRequest);
        assertNotNull(response);
        assertEquals(StatusEnum.SUCCESS, response.getStatus());
        assertEquals(SENDGRID_URI, response.getProvider());
    }

    @Test
    public void testFailover() {
        when(environmentProperty.getSendGridURI()).thenThrow(new SiteminderServicesException());
        when(environmentProperty.getMailgunURI()).thenReturn(MAILGUN_URI);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        sendGridEmailHandler.setNext(mailgunEmailHandler);
        sendGridEmailHandler.sendEmail(emailRequest);

        EmailResponse response = sendGridEmailHandler.sendEmail(emailRequest);
        assertNotNull(response);
        assertEquals(StatusEnum.SUCCESS, response.getStatus());
        assertEquals(MAILGUN_URI, response.getProvider());
    }

    @Test(expected = SiteminderServicesException.class)
    public void whenProvidersUnavailable() {
        when(environmentProperty.getMailgunURI()).thenThrow(new SiteminderServicesException());
        when(environmentProperty.getSendGridURI()).thenThrow(new SiteminderServicesException());
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        sendGridEmailHandler.setNext(mailgunEmailHandler);
        sendGridEmailHandler.sendEmail(emailRequest);
    }


}
