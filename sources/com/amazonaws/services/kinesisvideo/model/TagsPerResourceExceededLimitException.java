package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonServiceException;

public class TagsPerResourceExceededLimitException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public TagsPerResourceExceededLimitException(String message) {
        super(message);
    }
}
