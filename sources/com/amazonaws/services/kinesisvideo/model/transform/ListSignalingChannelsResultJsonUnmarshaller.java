package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ListSignalingChannelsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListSignalingChannelsResultJsonUnmarshaller implements Unmarshaller<ListSignalingChannelsResult, JsonUnmarshallerContext> {
    private static ListSignalingChannelsResultJsonUnmarshaller instance;

    public ListSignalingChannelsResult unmarshall(JsonUnmarshallerContext context) {
        ListSignalingChannelsResult listSignalingChannelsResult = new ListSignalingChannelsResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ChannelInfoList")) {
                listSignalingChannelsResult.setChannelInfoList(new ListUnmarshaller(ChannelInfoJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("NextToken")) {
                listSignalingChannelsResult.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listSignalingChannelsResult;
    }

    public static ListSignalingChannelsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListSignalingChannelsResultJsonUnmarshaller();
        }
        return instance;
    }
}
