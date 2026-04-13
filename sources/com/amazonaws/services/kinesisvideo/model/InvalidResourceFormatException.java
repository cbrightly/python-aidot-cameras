package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class InvalidResourceFormatException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InvalidResourceFormatException(String message) {
        super(message);
    }
}
