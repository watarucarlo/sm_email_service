package com.au.siteminder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class EmailRequest {

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String to;

    @Getter
    @Setter
    private List<String> cc;

    @Getter
    @Setter
    private List<String> bcc;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String text;
}
