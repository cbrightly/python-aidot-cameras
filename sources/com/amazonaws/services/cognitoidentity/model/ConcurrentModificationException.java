package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class ConcurrentModificationException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ConcurrentModificationException(String message) {
        super(message);
    }
}
