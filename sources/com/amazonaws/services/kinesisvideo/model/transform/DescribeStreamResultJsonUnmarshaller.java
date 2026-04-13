package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.DescribeStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DescribeStreamResultJsonUnmarshaller implements Unmarshaller<DescribeStreamResult, JsonUnmarshallerContext> {
    private static DescribeStreamResultJsonUnmarshaller instance;

    public DescribeStreamResult unmarshall(JsonUnmarshallerContext context) {
        DescribeStreamResult describeStreamResult = new DescribeStreamResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("StreamInfo")) {
                describeStreamResult.setStreamInfo(StreamInfoJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return describeStreamResult;
    }

    public static DescribeStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DescribeStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
