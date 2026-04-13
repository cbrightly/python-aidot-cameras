package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.DeleteStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class DeleteStreamResultJsonUnmarshaller implements Unmarshaller<DeleteStreamResult, JsonUnmarshallerContext> {
    private static DeleteStreamResultJsonUnmarshaller instance;

    public DeleteStreamResult unmarshall(JsonUnmarshallerContext context) {
        return new DeleteStreamResult();
    }

    public static DeleteStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
