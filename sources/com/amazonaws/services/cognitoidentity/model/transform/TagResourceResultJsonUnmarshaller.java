package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.TagResourceResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class TagResourceResultJsonUnmarshaller implements Unmarshaller<TagResourceResult, JsonUnmarshallerContext> {
    private static TagResourceResultJsonUnmarshaller instance;

    public TagResourceResult unmarshall(JsonUnmarshallerContext context) {
        return new TagResourceResult();
    }

    public static TagResourceResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new TagResourceResultJsonUnmarshaller();
        }
        return instance;
    }
}
