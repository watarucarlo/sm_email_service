package com.au.siteminder.framework;

import com.au.siteminder.framework.exception.SiteminderServicesException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.au.siteminder.framework.constant.SiteminderEmailServiceConstant.*;

@Component
public class EnvironmentProperty {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentProperty.class);

    @Autowired
    private Environment environment;

    public String getMailgunUser() {
        return getProperty(MAILGUN_USER_PROPERTY);
    }

    public String getMailgunKey() {
        return getProperty(MAILGUN_KEY_PROPERTY);
    }

    public String getMailgunURI() {
        return getProperty(MAILGUN_URI_PROPERTY);
    }

    public String getSendGridURI() {
        return getProperty(SENDGRID_URI_PROPERTY);
    }

    public String getSendGridKey() {
        return getProperty(SENDGRID_KEY_PROPERTY);
    }

    public String getProperty(String key) {
        String property = environment.getProperty(key);
        if (StringUtils.isEmpty(property)) {
            log.error("Missing environment property : " + key);
            throw new SiteminderServicesException("Missing environment property : " + key);
        }
        return property;
    }


}
