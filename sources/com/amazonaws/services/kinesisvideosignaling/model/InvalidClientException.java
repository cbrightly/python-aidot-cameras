package com.amazonaws.services.kinesisvideosignaling.model;

import com.amazonaws.AmazonServiceException;

public class InvalidClientException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InvalidClientException(String message) {
        super(message);
    }
}
