package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class VersionMismatchException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public VersionMismatchException(String message) {
        super(message);
    }
}
