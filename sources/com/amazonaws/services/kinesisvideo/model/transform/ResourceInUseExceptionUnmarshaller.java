package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.ResourceInUseException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ResourceInUseExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ResourceInUseExceptionUnmarshaller() {
        super(ResourceInUseException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("ResourceInUseException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        ResourceInUseException e = (ResourceInUseException) super.unmarshall(error);
        e.setErrorCode("ResourceInUseException");
        return e;
    }
}
