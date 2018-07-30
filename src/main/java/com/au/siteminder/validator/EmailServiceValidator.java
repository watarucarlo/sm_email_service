package com.au.siteminder.validator;

import com.au.siteminder.framework.exception.model.ValidationError;
import com.au.siteminder.framework.exception.model.ValidationErrorMessage;
import com.au.siteminder.framework.validator.ServiceValidator;
import com.au.siteminder.model.EmailRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EmailServiceValidator extends ServiceValidator {

    private static final String EMAIL_REQUEST = "email_request";

    public static void validate(EmailRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        checkMandatory(errors, EMAIL_REQUEST, request);

        if (request != null) {
            checkMandatoryString(errors, EmailRequest.FROM, request.getFrom());
            checkMandatoryString(errors, EmailRequest.TEXT, request.getText());
            if (StringUtils.isNotBlank(request.getFrom())) {
                validateEmailFormat(errors, EmailRequest.FROM, request.getFrom());
            }
            validateRecipients(errors, request);
        }
        handleErrors(errors);
    }


    private static void validateRecipients(List<ValidationError> errors, EmailRequest request) {
        if (CollectionUtils.isEmpty(request.getTo())) {
            errors.add(new ValidationError(EmailRequest.TO, ValidationErrorMessage.REQUIRED));
        } else {
            validateDuplicates(errors, request);
        }
    }

    private static void validateDuplicates(List<ValidationError> errors, EmailRequest request) {
        Set<String> recipients = new HashSet<>();

        recipients.addAll(validateDuplicates(errors, request.getTo(), recipients));
        recipients.addAll(validateDuplicates(errors, request.getCc(), recipients));
        recipients.addAll(validateDuplicates(errors, request.getBcc(), recipients));

        if (CollectionUtils.isNotEmpty(recipients)) {
            for (String email : recipients) {
                validateEmailFormat(errors, email, email);
            }
        }

    }

    private static Set<String> validateDuplicates(List<ValidationError> errors, List<String> recipientList, Set<String> recipients) {
        if (CollectionUtils.isNotEmpty(recipientList)) {
            for (String email : recipientList) {
                boolean accepted = recipients.add(email);
                if (!accepted) {
                    errors.add(new ValidationError(email, ValidationErrorMessage.DUPLICATE));
                }
            }
        }

        return recipients;
    }


}
