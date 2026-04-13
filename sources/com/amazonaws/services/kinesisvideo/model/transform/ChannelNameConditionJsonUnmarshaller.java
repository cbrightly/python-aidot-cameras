package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ChannelNameCondition;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ChannelNameConditionJsonUnmarshaller implements Unmarshaller<ChannelNameCondition, JsonUnmarshallerContext> {
    private static ChannelNameConditionJsonUnmarshaller instance;

    ChannelNameConditionJsonUnmarshaller() {
    }

    public ChannelNameCondition unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ChannelNameCondition channelNameCondition = new ChannelNameCondition();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ComparisonOperator")) {
                channelNameCondition.setComparisonOperator(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ComparisonValue")) {
                channelNameCondition.setComparisonValue(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return channelNameCondition;
    }

    public static ChannelNameConditionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelNameConditionJsonUnmarshaller();
        }
        return instance;
    }
}
