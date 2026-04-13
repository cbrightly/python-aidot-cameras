package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class AccessDeniedException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public AccessDeniedException(String message) {
        super(message);
    }
}
