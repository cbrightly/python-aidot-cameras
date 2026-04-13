package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.UpdateSignalingChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class UpdateSignalingChannelResultJsonUnmarshaller implements Unmarshaller<UpdateSignalingChannelResult, JsonUnmarshallerContext> {
    private static UpdateSignalingChannelResultJsonUnmarshaller instance;

    public UpdateSignalingChannelResult unmarshall(JsonUnmarshallerContext context) {
        return new UpdateSignalingChannelResult();
    }

    public static UpdateSignalingChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateSignalingChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
