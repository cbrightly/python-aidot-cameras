package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.UpdateDataRetentionResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class UpdateDataRetentionResultJsonUnmarshaller implements Unmarshaller<UpdateDataRetentionResult, JsonUnmarshallerContext> {
    private static UpdateDataRetentionResultJsonUnmarshaller instance;

    public UpdateDataRetentionResult unmarshall(JsonUnmarshallerContext context) {
        return new UpdateDataRetentionResult();
    }

    public static UpdateDataRetentionResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateDataRetentionResultJsonUnmarshaller();
        }
        return instance;
    }
}
