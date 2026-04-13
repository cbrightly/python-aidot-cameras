package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentity.model.DeveloperUserAlreadyRegisteredException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class DeveloperUserAlreadyRegisteredExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public DeveloperUserAlreadyRegisteredExceptionUnmarshaller() {
        super(DeveloperUserAlreadyRegisteredException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("DeveloperUserAlreadyRegisteredException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        DeveloperUserAlreadyRegisteredException e = (DeveloperUserAlreadyRegisteredException) super.unmarshall(error);
        e.setErrorCode("DeveloperUserAlreadyRegisteredException");
        return e;
    }
}
