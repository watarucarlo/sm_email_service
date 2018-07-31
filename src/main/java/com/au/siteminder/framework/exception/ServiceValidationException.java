package com.au.siteminder.framework.exception;

import com.au.siteminder.framework.exception.model.ValidationError;
import com.au.siteminder.framework.exception.model.ValidationErrorMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception class used for validation errors
 *
 */
public class ServiceValidationException extends RuntimeException {

    @Getter
    @Setter
    private List<ValidationError> errors;

    public ServiceValidationException() {

    }

    public ServiceValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public ServiceValidationException(ValidationError validationError) {
        this.errors = new ArrayList<>();
        errors.add(validationError);
    }

    public ServiceValidationException(String label, ValidationErrorMessage validationErrorMessage) {
        this.errors = new ArrayList<>();
        errors.add(new ValidationError(label, validationErrorMessage));
    }

    public String getMessage() {
        return this.errors.toString();
    }
}
