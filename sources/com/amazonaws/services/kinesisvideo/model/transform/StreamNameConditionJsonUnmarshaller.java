package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.StreamNameCondition;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class StreamNameConditionJsonUnmarshaller implements Unmarshaller<StreamNameCondition, JsonUnmarshallerContext> {
    private static StreamNameConditionJsonUnmarshaller instance;

    StreamNameConditionJsonUnmarshaller() {
    }

    public StreamNameCondition unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        StreamNameCondition streamNameCondition = new StreamNameCondition();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ComparisonOperator")) {
                streamNameCondition.setComparisonOperator(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ComparisonValue")) {
                streamNameCondition.setComparisonValue(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return streamNameCondition;
    }

    public static StreamNameConditionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new StreamNameConditionJsonUnmarshaller();
        }
        return instance;
    }
}
