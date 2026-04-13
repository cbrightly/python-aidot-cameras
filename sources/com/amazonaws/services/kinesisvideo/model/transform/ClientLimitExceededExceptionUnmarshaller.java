package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.ClientLimitExceededException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ClientLimitExceededExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ClientLimitExceededExceptionUnmarshaller() {
        super(ClientLimitExceededException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("ClientLimitExceededException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        ClientLimitExceededException e = (ClientLimitExceededException) super.unmarshall(error);
        e.setErrorCode("ClientLimitExceededException");
        return e;
    }
}
