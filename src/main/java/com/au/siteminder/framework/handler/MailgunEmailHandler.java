package com.au.siteminder.framework.handler;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.model.common.StatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Handler class used to send emails using Mailgun api
 */
@Component
public class MailgunEmailHandler extends EmailHandler {

    private static final Logger log = LoggerFactory.getLogger(MailgunEmailHandler.class);

    @Autowired
    private EnvironmentProperty environmentProperty;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    /**
     * Method used to send emails via mailgun
     *
     * @param emailRequest
     * @return emailResponse
     */
    public EmailResponse sendEmail(EmailRequest emailRequest) {
        try {
            return sendMailViaMailgun(emailRequest);
        } catch (Exception e) {
            if (next == null) {
                log.error("Email providers are not available");
                throw new SiteminderServicesException("Email providers not available");
            }
            return next.sendEmail(emailRequest);
        }
    }

    private EmailResponse sendMailViaMailgun(EmailRequest emailRequest) {
        restTemplate = getRestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = createHttpRequest(emailRequest);
        log.info("Sending email via mailgun...");
        try {
            restTemplate.postForLocation(environmentProperty.getMailgunURI(), request);
        } catch (Exception e) {
            log.error("Failed to send email via mailgun...", e);
            throw new SiteminderServicesException("Failed to send email via mailgun...", e);
        }
        log.info("Successfully sent email via mailgun...");
        return createResponse();
    }

    /**
     * Creates HttpEntity request object from an email request object
     *
     * @param emailRequest
     * @return
     */
    private HttpEntity<MultiValueMap<String, String>> createHttpRequest(EmailRequest emailRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(EmailRequest.FROM, emailRequest.getFrom());
        if (CollectionUtils.isNotEmpty(emailRequest.getTo())) {
            map.put(EmailRequest.TO, emailRequest.getTo());
        }
        if (CollectionUtils.isNotEmpty(emailRequest.getCc())) {
            map.put(EmailRequest.CC, emailRequest.getCc());
        }
        if (CollectionUtils.isNotEmpty(emailRequest.getBcc())) {
            map.put(EmailRequest.BCC, emailRequest.getBcc());
        }
        map.add(EmailRequest.SUBJECT, emailRequest.getSubject());
        map.add(EmailRequest.TEXT, emailRequest.getText());

        return new HttpEntity<>(map, headers);
    }

    /**
     * Creates an email response object
     *
     * @return
     */
    private EmailResponse createResponse() {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setStatus(StatusEnum.SUCCESS);
        emailResponse.setProvider(environmentProperty.getMailgunURI());
        return emailResponse;
    }

    /**
     * Creates a rest template and add necessary interceptors
     *
     * @return
     */
    private RestTemplate getRestTemplate() {
        restTemplate = restTemplateBuilder.build();

        //Add authorization headers
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(environmentProperty.getMailgunUser(),
                        environmentProperty.getMailgunKey()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });
        return restTemplate;
    }

}
