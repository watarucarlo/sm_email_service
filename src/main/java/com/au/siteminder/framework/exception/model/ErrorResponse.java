package com.au.siteminder.framework.exception.model;

import lombok.Getter;
import lombok.Setter;

public class ErrorResponse {

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String message;

}
