package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ListStreamsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListStreamsResultJsonUnmarshaller implements Unmarshaller<ListStreamsResult, JsonUnmarshallerContext> {
    private static ListStreamsResultJsonUnmarshaller instance;

    public ListStreamsResult unmarshall(JsonUnmarshallerContext context) {
        ListStreamsResult listStreamsResult = new ListStreamsResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("StreamInfoList")) {
                listStreamsResult.setStreamInfoList(new ListUnmarshaller(StreamInfoJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("NextToken")) {
                listStreamsResult.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listStreamsResult;
    }

    public static ListStreamsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListStreamsResultJsonUnmarshaller();
        }
        return instance;
    }
}
