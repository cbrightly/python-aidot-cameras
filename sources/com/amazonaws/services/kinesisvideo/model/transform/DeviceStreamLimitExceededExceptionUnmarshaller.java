package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.DeviceStreamLimitExceededException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class DeviceStreamLimitExceededExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public DeviceStreamLimitExceededExceptionUnmarshaller() {
        super(DeviceStreamLimitExceededException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("DeviceStreamLimitExceededException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        DeviceStreamLimitExceededException e = (DeviceStreamLimitExceededException) super.unmarshall(error);
        e.setErrorCode("DeviceStreamLimitExceededException");
        return e;
    }
}
