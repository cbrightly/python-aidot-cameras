package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.UntagStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class UntagStreamResultJsonUnmarshaller implements Unmarshaller<UntagStreamResult, JsonUnmarshallerContext> {
    private static UntagStreamResultJsonUnmarshaller instance;

    public UntagStreamResult unmarshall(JsonUnmarshallerContext context) {
        return new UntagStreamResult();
    }

    public static UntagStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UntagStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
