package com.amazonaws.services.kinesisvideosignaling.model;

import com.amazonaws.AmazonServiceException;

public class InvalidArgumentException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InvalidArgumentException(String message) {
        super(message);
    }
}
