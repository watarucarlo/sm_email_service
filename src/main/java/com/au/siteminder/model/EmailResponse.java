package com.au.siteminder.model;

import com.au.siteminder.model.common.StatusEnum;
import lombok.Getter;
import lombok.Setter;

public class EmailResponse {

    @Getter
    @Setter
    private StatusEnum status;

    @Getter
    @Setter
    private String provider;
}
