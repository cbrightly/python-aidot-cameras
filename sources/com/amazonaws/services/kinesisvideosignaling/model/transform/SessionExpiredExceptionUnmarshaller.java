package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideosignaling.model.SessionExpiredException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class SessionExpiredExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public SessionExpiredExceptionUnmarshaller() {
        super(SessionExpiredException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("SessionExpiredException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        SessionExpiredException e = (SessionExpiredException) super.unmarshall(error);
        e.setErrorCode("SessionExpiredException");
        return e;
    }
}
