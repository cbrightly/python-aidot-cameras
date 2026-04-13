package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.DeleteSignalingChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class DeleteSignalingChannelResultJsonUnmarshaller implements Unmarshaller<DeleteSignalingChannelResult, JsonUnmarshallerContext> {
    private static DeleteSignalingChannelResultJsonUnmarshaller instance;

    public DeleteSignalingChannelResult unmarshall(JsonUnmarshallerContext context) {
        return new DeleteSignalingChannelResult();
    }

    public static DeleteSignalingChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteSignalingChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
