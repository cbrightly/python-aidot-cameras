package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.kinesisvideo.model.TagsPerResourceExceededLimitException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class TagsPerResourceExceededLimitExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public TagsPerResourceExceededLimitExceptionUnmarshaller() {
        super(TagsPerResourceExceededLimitException.class);
    }

    public boolean match(JsonErrorResponseHandler.JsonErrorResponse error) {
        return error.getErrorCode().equals("TagsPerResourceExceededLimitException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse error) {
        TagsPerResourceExceededLimitException e = (TagsPerResourceExceededLimitException) super.unmarshall(error);
        e.setErrorCode("TagsPerResourceExceededLimitException");
        return e;
    }
}
