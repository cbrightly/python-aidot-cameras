package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.VersionMismatchException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class VersionMismatchExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public VersionMismatchExceptionUnmarshaller() {
        super(VersionMismatchException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("VersionMismatchException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        VersionMismatchException e = (VersionMismatchException) super.unmarshall(error);
        e.setErrorCode("VersionMismatchException");
        return e;
    }
}
