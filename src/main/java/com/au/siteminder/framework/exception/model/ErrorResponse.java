package com.au.siteminder.framework.exception.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Response object used for converting exceptions to a more readable format
 *
 */
public class ErrorResponse {

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String message;

}
