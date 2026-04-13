package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class DeveloperUserAlreadyRegisteredException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public DeveloperUserAlreadyRegisteredException(String message) {
        super(message);
    }
}
