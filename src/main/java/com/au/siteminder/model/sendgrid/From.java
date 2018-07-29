package com.au.siteminder.model.sendgrid;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

public class From {

    public From() {

    }

    public From(String email) {
        this.email=email;
    }

    @Getter
    @Setter
    @XmlElement(name = "email")
    private String email;
}
