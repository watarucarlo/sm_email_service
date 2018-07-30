package com.au.siteminder.framework.handler;

import com.au.siteminder.framework.exception.ServiceValidationException;
import com.au.siteminder.framework.exception.SiteminderServicesException;
import com.au.siteminder.framework.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(ServiceValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationError(ServiceValidationException sve) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(sve.getMessage());
        errorResponse.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errorResponse;
    }

    @ExceptionHandler(SiteminderServicesException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleSiteMinderException(SiteminderServicesException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return errorResponse;
    }

}
