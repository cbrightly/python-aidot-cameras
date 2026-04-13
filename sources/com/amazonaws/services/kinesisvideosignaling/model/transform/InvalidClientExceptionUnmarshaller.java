package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideosignaling.model.InvalidClientException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidClientExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidClientExceptionUnmarshaller() {
        super(InvalidClientException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("InvalidClientException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        InvalidClientException e = (InvalidClientException) super.unmarshall(error);
        e.setErrorCode("InvalidClientException");
        return e;
    }
}
