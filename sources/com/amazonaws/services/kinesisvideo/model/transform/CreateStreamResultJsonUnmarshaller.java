package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.CreateStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class CreateStreamResultJsonUnmarshaller implements Unmarshaller<CreateStreamResult, JsonUnmarshallerContext> {
    private static CreateStreamResultJsonUnmarshaller instance;

    public CreateStreamResult unmarshall(JsonUnmarshallerContext context) {
        CreateStreamResult createStreamResult = new CreateStreamResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("StreamARN")) {
                createStreamResult.setStreamARN(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return createStreamResult;
    }

    public static CreateStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
