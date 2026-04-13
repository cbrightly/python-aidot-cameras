package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.UpdateStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class UpdateStreamResultJsonUnmarshaller implements Unmarshaller<UpdateStreamResult, JsonUnmarshallerContext> {
    private static UpdateStreamResultJsonUnmarshaller instance;

    public UpdateStreamResult unmarshall(JsonUnmarshallerContext context) {
        return new UpdateStreamResult();
    }

    public static UpdateStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
