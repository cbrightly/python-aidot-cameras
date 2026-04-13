package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.LimitExceededException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class LimitExceededExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public LimitExceededExceptionUnmarshaller() {
        super(LimitExceededException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("LimitExceededException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        LimitExceededException e = (LimitExceededException) super.unmarshall(error);
        e.setErrorCode("LimitExceededException");
        return e;
    }
}
