package com.au.siteminder.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

public class EmailResponse {

    @Getter
    @Setter
    @XmlElement(name = "id")
    private String id;

    @Getter
    @Setter
    @XmlElement(name = "message")
    private String message;
}
