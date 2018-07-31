package com.au.siteminder.framework.validator;

import com.au.siteminder.framework.exception.ServiceValidationException;
import com.au.siteminder.framework.exception.model.ValidationError;
import com.au.siteminder.framework.exception.model.ValidationErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public class ServiceValidator {

    protected static void checkMandatory(List<ValidationError> errors, String label, Object value) {
        if (value == null) {
            errors.add(new ValidationError(label, ValidationErrorMessage.REQUIRED));
        }
    }


    protected static void checkMandatoryString(List<ValidationError> errors, String label, String value) {
        if (StringUtils.isBlank(value)) {
            errors.add(new ValidationError(label, ValidationErrorMessage.REQUIRED));
        }
    }

    protected static void handleErrors(List<ValidationError> errors) {
        if (errors.size() > 0) {
            throw new ServiceValidationException(errors);
        }
    }

    protected static void validateEmailFormat(List<ValidationError> errors, String label, String value) {
        if (value == null) {
            errors.add(new ValidationError(label, ValidationErrorMessage.EMAIL_FORMAT_INVALID));
        } else {
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(value)) {
                errors.add(new ValidationError(label, ValidationErrorMessage.EMAIL_FORMAT_INVALID));
            }

        }
    }

}
