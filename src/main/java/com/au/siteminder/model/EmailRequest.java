package com.au.siteminder.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Request object for sending emails. Note that this only supports plain text messages and no attachments
 *
 */
public class EmailRequest implements Serializable {

    private static final long serialVersionUID = 2359312660372242694L;

    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String CC = "cc";
    public static final String BCC = "bcc";
    public static final String SUBJECT = "subject";
    public static final String TEXT = "text";

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private List<String> to;

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
