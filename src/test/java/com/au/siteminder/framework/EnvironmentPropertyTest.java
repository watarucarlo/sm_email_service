package com.au.siteminder.framework;

import com.au.siteminder.framework.exception.SiteminderServicesException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static com.au.siteminder.framework.constant.SiteminderEmailServiceConstant.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class EnvironmentPropertyTest {

    private static final String VALUE = "tadaaaa";

    @Mock
    private Environment environment;

    @InjectMocks
    private EnvironmentProperty environmentProperty = new EnvironmentProperty();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(environment.getProperty(MAILGUN_KEY_PROPERTY)).thenReturn(VALUE);
        when(environment.getProperty(MAILGUN_URI_PROPERTY)).thenReturn(VALUE);
        when(environment.getProperty(MAILGUN_USER_PROPERTY)).thenReturn(VALUE);
        when(environment.getProperty(SENDGRID_KEY_PROPERTY)).thenReturn(VALUE);
        when(environment.getProperty(SENDGRID_URI_PROPERTY)).thenReturn(VALUE);
    }

    @Test
    public void whenGetPropertyNotNull() {
        String s = environmentProperty.getProperty(MAILGUN_KEY_PROPERTY);
        assertEquals(VALUE, s);
    }

    @Test(expected = SiteminderServicesException.class)
    public void whenGetPropertyNull() {
        environmentProperty.getProperty("asdasdasdasd");
    }

    @Test
    public void testEnvironmentPropertyGetters() {
        environmentProperty.getSendGridKey();
        environmentProperty.getSendGridURI();
        environmentProperty.getMailgunURI();
        environmentProperty.getMailgunKey();
        environmentProperty.getMailgunUser();
    }


}
