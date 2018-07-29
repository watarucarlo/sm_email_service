package com.au.siteminder.model;

public class EmailResponseMother {

    public static final String ID = "abcd";
    public static final String MESSAGE = "test";

    public static EmailResponse build() {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setId(ID);
        emailResponse.setMessage(MESSAGE);
        return emailResponse;
    }

}
