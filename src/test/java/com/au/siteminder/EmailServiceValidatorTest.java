package com.au.siteminder;

import com.au.siteminder.framework.exception.ServiceValidationException;
import com.au.siteminder.model.EmailRequest;
import com.au.siteminder.model.EmailRequestMother;
import com.au.siteminder.validator.EmailServiceValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class EmailServiceValidatorTest {

    private EmailServiceValidator validator = new EmailServiceValidator();

    private EmailRequest request;

    @Before
    public void init() {
        request = EmailRequestMother.build();
    }

    @Test(expected = ServiceValidationException.class)
    public void testValidate_nullEmailRequest() {
        validator.validate(null);
    }

    @Test
    public void testValidateEmailRequest() {
        validator.validate(request);
    }

    @Test
    public void testValidateEmailRequest_Mandatories() {
        request.setFrom(null);
        request.setTo(null);
        request.setText(null);
        try {
            validator.validate(request);
            Assert.fail("Should have failed");
        } catch (ServiceValidationException e) {
            Assert.assertEquals(3, e.getErrors().size());
        }
    }

    @Test
    public void testValidateEmailRequest_Duplicate() {
        request.setTo(Collections.singletonList("test1@test.com"));
        request.setCc(Collections.singletonList("test1@test.com"));
        request.setBcc(Collections.singletonList("test1@test.com"));
        try {
            validator.validate(request);
            Assert.fail("Should have failed");
        } catch (ServiceValidationException e) {
            Assert.assertEquals(2, e.getErrors().size());
        }
    }


}
