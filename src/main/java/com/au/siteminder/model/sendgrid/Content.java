package com.au.siteminder.model.sendgrid;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

public class Content {

    public Content() {

    }

    public Content(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Getter
    @Setter
    @XmlElement(name = "type")
    private String type;

    @Getter
    @Setter
    @XmlElement(name = "value")
    private String value;

}
