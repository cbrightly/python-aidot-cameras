package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.ConcurrentModificationException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ConcurrentModificationExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ConcurrentModificationExceptionUnmarshaller() {
        super(ConcurrentModificationException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("ConcurrentModificationException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        ConcurrentModificationException e = (ConcurrentModificationException) super.unmarshall(error);
        e.setErrorCode("ConcurrentModificationException");
        return e;
    }
}
