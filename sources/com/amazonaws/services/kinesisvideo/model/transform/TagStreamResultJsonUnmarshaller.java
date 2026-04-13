package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.TagStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class TagStreamResultJsonUnmarshaller implements Unmarshaller<TagStreamResult, JsonUnmarshallerContext> {
    private static TagStreamResultJsonUnmarshaller instance;

    public TagStreamResult unmarshall(JsonUnmarshallerContext context) {
        return new TagStreamResult();
    }

    public static TagStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new TagStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
