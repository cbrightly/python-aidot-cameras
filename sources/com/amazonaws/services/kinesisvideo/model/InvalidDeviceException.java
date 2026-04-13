package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class InvalidDeviceException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InvalidDeviceException(String message) {
        super(message);
    }
}
