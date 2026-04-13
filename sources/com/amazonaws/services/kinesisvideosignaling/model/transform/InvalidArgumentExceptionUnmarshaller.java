package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideosignaling.model.InvalidArgumentException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidArgumentExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidArgumentExceptionUnmarshaller() {
        super(InvalidArgumentException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("InvalidArgumentException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        InvalidArgumentException e = (InvalidArgumentException) super.unmarshall(error);
        e.setErrorCode("InvalidArgumentException");
        return e;
    }
}
