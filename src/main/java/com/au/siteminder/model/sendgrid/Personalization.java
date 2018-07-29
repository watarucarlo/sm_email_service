package com.au.siteminder.model.sendgrid;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Personalization {

    @Getter
    @Setter
    @XmlElement(name = "to")
    private List<To> to;

    @Getter
    @Setter
    @XmlElement(name = "cc")
    private List<CC> cc;

    @Getter
    @Setter
    @XmlElement(name = "bcc")
    private List<BCC> bcc;
}
