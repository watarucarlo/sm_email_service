package com.au.siteminder.model;

import com.au.siteminder.model.common.StatusEnum;

public class EmailResponseMother {

    public static final StatusEnum STATUS = StatusEnum.SUCCESS;
    public static final String PROVIDER = "test";

    public static EmailResponse build() {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setStatus(STATUS);
        emailResponse.setProvider(PROVIDER);
        return emailResponse;
    }

}
