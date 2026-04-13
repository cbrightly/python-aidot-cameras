package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.AccountStreamLimitExceededException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class AccountStreamLimitExceededExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public AccountStreamLimitExceededExceptionUnmarshaller() {
        super(AccountStreamLimitExceededException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("AccountStreamLimitExceededException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        AccountStreamLimitExceededException e = (AccountStreamLimitExceededException) super.unmarshall(error);
        e.setErrorCode("AccountStreamLimitExceededException");
        return e;
    }
}
