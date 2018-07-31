package com.au.siteminder.model.sendgrid;

import lombok.Getter;
import lombok.Setter;

public class Recipient {

    public Recipient() {

    }

    public Recipient(String email) {
        this.email = email;
    }

    @Getter
    @Setter
    private String email;

}
