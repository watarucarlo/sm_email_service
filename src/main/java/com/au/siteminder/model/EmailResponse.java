package com.au.siteminder.model;

import com.au.siteminder.model.common.StatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for sending emails. Status field tells you whether the email sending process is successful and the provider
 * field tells you which provider was used to send the email
 *
 */
public class EmailResponse {

    @Getter
    @Setter
    private StatusEnum status;

    @Getter
    @Setter
    private String provider;
}
