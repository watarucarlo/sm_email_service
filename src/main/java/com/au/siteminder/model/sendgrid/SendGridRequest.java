package com.au.siteminder.model.sendgrid;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class SendGridRequest {

    @Getter
    @Setter
    @XmlElement(name = "personalizations")
    private List<Personalization> personalizations;

    @Getter
    @Setter
    @XmlElement(name = "subject")
    private String subject;

    @Getter
    @Setter
    @XmlElement(name = "from")
    private From from;

    @Getter
    @Setter
    @XmlElement(name = "content")
    private List<Content> content;
}
