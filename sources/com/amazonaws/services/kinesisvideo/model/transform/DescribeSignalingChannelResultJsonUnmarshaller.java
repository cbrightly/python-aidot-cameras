package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.DescribeSignalingChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DescribeSignalingChannelResultJsonUnmarshaller implements Unmarshaller<DescribeSignalingChannelResult, JsonUnmarshallerContext> {
    private static DescribeSignalingChannelResultJsonUnmarshaller instance;

    public DescribeSignalingChannelResult unmarshall(JsonUnmarshallerContext context) {
        DescribeSignalingChannelResult describeSignalingChannelResult = new DescribeSignalingChannelResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("ChannelInfo")) {
                describeSignalingChannelResult.setChannelInfo(ChannelInfoJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return describeSignalingChannelResult;
    }

    public static DescribeSignalingChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DescribeSignalingChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
