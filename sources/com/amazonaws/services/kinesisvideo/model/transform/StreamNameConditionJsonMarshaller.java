package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.StreamNameCondition;
import com.amazonaws.util.json.AwsJsonWriter;

public class StreamNameConditionJsonMarshaller {
    private static StreamNameConditionJsonMarshaller instance;

    StreamNameConditionJsonMarshaller() {
    }

    public void marshall(StreamNameCondition streamNameCondition, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (streamNameCondition.getComparisonOperator() != null) {
            String comparisonOperator = streamNameCondition.getComparisonOperator();
            jsonWriter.name("ComparisonOperator");
            jsonWriter.value(comparisonOperator);
        }
        if (streamNameCondition.getComparisonValue() != null) {
            String comparisonValue = streamNameCondition.getComparisonValue();
            jsonWriter.name("ComparisonValue");
            jsonWriter.value(comparisonValue);
        }
        jsonWriter.endObject();
    }

    public static StreamNameConditionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new StreamNameConditionJsonMarshaller();
        }
        return instance;
    }
}
