package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.InvalidResourceFormatException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidResourceFormatExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidResourceFormatExceptionUnmarshaller() {
        super(InvalidResourceFormatException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("InvalidResourceFormatException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        InvalidResourceFormatException e = (InvalidResourceFormatException) super.unmarshall(error);
        e.setErrorCode("InvalidResourceFormatException");
        return e;
    }
}
