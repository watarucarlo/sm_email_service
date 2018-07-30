package com.au.siteminder.framework.handler;

import com.au.siteminder.framework.EnvironmentProperty;
import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.model.common.StatusEnum;
import com.au.siteminder.model.sendgrid.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SendGridEmailHandler extends EmailHandler {

    private static final Logger log = LoggerFactory.getLogger(SendGridEmailHandler.class);

    @Autowired
    private EnvironmentProperty environmentProperty;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        try {
            return sendMailViaSendGrid(emailRequest);
        } catch (Exception e) {
            if (next == null) {
                log.error("Email providers are not available");
                throw new SiteminderServicesException("Email providers not available");
            }
            return next.sendEmail(emailRequest);
        }
    }

    private SendGridRequest createSendGridRequest(EmailRequest emailRequest) {
        SendGridRequest sendGridRequest = new SendGridRequest();
        Content content = new Content("text/plain", emailRequest.getText());
        sendGridRequest.setContent(Collections.singletonList(content));
        sendGridRequest.setSubject(emailRequest.getSubject());
        From from = new From(emailRequest.getFrom());
        sendGridRequest.setFrom(from);

        List<To> toList = CollectionUtils.isEmpty(emailRequest.getTo()) ? null : emailRequest.getTo().stream().map(to -> new To(to)).collect(Collectors.toList());
        List<CC> ccList = CollectionUtils.isEmpty(emailRequest.getCc()) ? null : emailRequest.getCc().stream().map(cc -> new CC(cc)).collect(Collectors.toList());
        List<BCC> bccList = CollectionUtils.isEmpty(emailRequest.getBcc()) ? null : emailRequest.getBcc().stream().map(bcc -> new BCC(bcc)).collect(Collectors.toList());

        Personalization personalization = new Personalization();
        if (toList != null) {
            personalization.setTo(toList);
        }
        if (ccList != null) {
            personalization.setCc(ccList);
        }
        if (bccList != null) {
            personalization.setBcc(bccList);
        }

        List<Personalization> personalizations = new ArrayList<>();
        personalizations.add(personalization);

        sendGridRequest.setPersonalizations(personalizations);
        return sendGridRequest;
    }

    private String convertRequestToJson(SendGridRequest sendGridRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            return objectMapper.writeValueAsString(sendGridRequest);
        } catch (Exception e) {
            log.error("Failed to parse request to json.", e);
            throw new SiteminderServicesException("Failed to parse request to json.", e);
        }
    }

    private EmailResponse sendMailViaSendGrid(EmailRequest emailRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + environmentProperty.getSendGridKey());

        HttpEntity<String> entity = new HttpEntity<String>(convertRequestToJson(createSendGridRequest(emailRequest)), headers);
        restTemplate = restTemplateBuilder.build();
        log.info("Sending email via sendgrid...");
        try {
            restTemplate.postForLocation(environmentProperty.getSendGridURI(), entity);
        } catch (Exception e) {
            log.error("Failed to send email via sendgrid...", e);
            throw new SiteminderServicesException("Failed to send email via sendgrid...", e);
        }
        log.info("Successfully sent email via sendgrid...");
        return createResponse();
    }

    private EmailResponse createResponse() {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setStatus(StatusEnum.SUCCESS);
        emailResponse.setProvider(environmentProperty.getSendGridURI());
        return emailResponse;
    }

}
