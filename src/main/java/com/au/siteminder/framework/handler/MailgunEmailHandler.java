package com.au.siteminder.framework.handler;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.model.common.StatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class MailgunEmailHandler extends EmailHandler {

    public MailgunEmailHandler(EnvironmentProperty environmentProperty, RestTemplateBuilder restTemplateBuilder) {
        this.environmentProperty = environmentProperty;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        try {
            return sendMailViaMailgun(emailRequest);
        } catch (Exception e) {
            if (next == null) {
                throw new SiteminderServicesException("Email providers not available");
            }
            return next.sendEmail(emailRequest);
        }
    }

    private EmailResponse sendMailViaMailgun(EmailRequest emailRequest) {
        RestTemplate restTemplate = getRestTemplate();

        HttpEntity<MultiValueMap<String, String>> request = createHttpRequest(emailRequest);

        restTemplate.postForLocation(environmentProperty.getMailgunURI(), request);

        return createResponse();
    }

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

    private EmailResponse createResponse() {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setStatus(StatusEnum.SUCCESS);
        emailResponse.setProvider(environmentProperty.getMailgunURI());
        return emailResponse;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = restTemplateBuilder.build();
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
