package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class AccountStreamLimitExceededException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public AccountStreamLimitExceededException(String message) {
        super(message);
    }
}
