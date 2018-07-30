package com.au.siteminder.controller;


import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.POST, value = "/send")
    public EmailResponse sendEmail(@RequestBody EmailRequest emailRequest) {
        return emailService.sendEmail(emailRequest);
    }

    @GetMapping(value = "/status")
    public String checkStatus() {
        return "OK";
    }

}
