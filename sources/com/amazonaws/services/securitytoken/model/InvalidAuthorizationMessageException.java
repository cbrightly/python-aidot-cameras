package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonServiceException;

public class InvalidAuthorizationMessageException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InvalidAuthorizationMessageException(String message) {
        super(message);
    }
}
