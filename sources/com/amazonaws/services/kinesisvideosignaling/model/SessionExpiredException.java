package com.amazonaws.services.kinesisvideosignaling.model;

import com.amazonaws.AmazonServiceException;

public class SessionExpiredException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public SessionExpiredException(String message) {
        super(message);
    }
}
