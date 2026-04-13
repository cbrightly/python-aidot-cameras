package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class AccountChannelLimitExceededException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public AccountChannelLimitExceededException(String message) {
        super(message);
    }
}
