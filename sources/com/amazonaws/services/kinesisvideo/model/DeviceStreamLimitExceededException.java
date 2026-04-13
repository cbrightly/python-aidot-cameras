package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class DeviceStreamLimitExceededException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public DeviceStreamLimitExceededException(String message) {
        super(message);
    }
}
