package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.InvalidDeviceException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidDeviceExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidDeviceExceptionUnmarshaller() {
        super(InvalidDeviceException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("InvalidDeviceException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        InvalidDeviceException e = (InvalidDeviceException) super.unmarshall(error);
        e.setErrorCode("InvalidDeviceException");
        return e;
    }
}
