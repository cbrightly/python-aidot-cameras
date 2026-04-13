package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.AccountChannelLimitExceededException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class AccountChannelLimitExceededExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public AccountChannelLimitExceededExceptionUnmarshaller() {
        super(AccountChannelLimitExceededException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("AccountChannelLimitExceededException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        AccountChannelLimitExceededException e = (AccountChannelLimitExceededException) super.unmarshall(error);
        e.setErrorCode("AccountChannelLimitExceededException");
        return e;
    }
}
