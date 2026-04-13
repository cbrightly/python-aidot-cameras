package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.CreateSignalingChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class CreateSignalingChannelResultJsonUnmarshaller implements Unmarshaller<CreateSignalingChannelResult, JsonUnmarshallerContext> {
    private static CreateSignalingChannelResultJsonUnmarshaller instance;

    public CreateSignalingChannelResult unmarshall(JsonUnmarshallerContext context) {
        CreateSignalingChannelResult createSignalingChannelResult = new CreateSignalingChannelResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("ChannelARN")) {
                createSignalingChannelResult.setChannelARN(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return createSignalingChannelResult;
    }

    public static CreateSignalingChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateSignalingChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
