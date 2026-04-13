package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.AccessDeniedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class AccessDeniedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public AccessDeniedExceptionUnmarshaller() {
        super(AccessDeniedException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("AccessDeniedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        AccessDeniedException e = (AccessDeniedException) super.unmarshall(error);
        e.setErrorCode("AccessDeniedException");
        return e;
    }
}
