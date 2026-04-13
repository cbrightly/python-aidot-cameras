package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ChannelNameCondition;
import com.amazonaws.util.json.AwsJsonWriter;

public class ChannelNameConditionJsonMarshaller {
    private static ChannelNameConditionJsonMarshaller instance;

    ChannelNameConditionJsonMarshaller() {
    }

    public void marshall(ChannelNameCondition channelNameCondition, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (channelNameCondition.getComparisonOperator() != null) {
            String comparisonOperator = channelNameCondition.getComparisonOperator();
            jsonWriter.name("ComparisonOperator");
            jsonWriter.value(comparisonOperator);
        }
        if (channelNameCondition.getComparisonValue() != null) {
            String comparisonValue = channelNameCondition.getComparisonValue();
            jsonWriter.name("ComparisonValue");
            jsonWriter.value(comparisonValue);
        }
        jsonWriter.endObject();
    }

    public static ChannelNameConditionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelNameConditionJsonMarshaller();
        }
        return instance;
    }
}
