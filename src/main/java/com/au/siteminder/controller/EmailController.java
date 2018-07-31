package com.au.siteminder.controller;


import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailResponse;
import com.au.siteminder.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Main controller used by the email microservice.
 */

@Api(value = "Email Controller", description = "Service endpoints used for sending emails")
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "Sends a plain text email to one or more recipients", response = EmailResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully queued an email for sending"),
            @ApiResponse(code = 400, message = "One or more request fields did not pass the validations"),
            @ApiResponse(code = 500, message = "Something bad happened while processing your request")
    }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/send")
    public EmailResponse sendEmail(@RequestBody EmailRequest emailRequest) {
        return emailService.sendEmail(emailRequest);
    }

    @ApiOperation(value = "A simple endpoint used to check whether the microservice is alive", response = String.class)
    @GetMapping(value = "/status")
    public String checkStatus() {
        return "OK";
    }

}
