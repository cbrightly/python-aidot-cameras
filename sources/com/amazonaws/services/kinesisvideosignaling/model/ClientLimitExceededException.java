package com.amazonaws.services.kinesisvideosignaling.model;

import com.amazonaws.AmazonServiceException;

public class ClientLimitExceededException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ClientLimitExceededException(String message) {
        super(message);
    }
}
