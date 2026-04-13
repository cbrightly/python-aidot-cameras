package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.SingleMasterConfiguration;
import com.amazonaws.util.json.AwsJsonWriter;

public class SingleMasterConfigurationJsonMarshaller {
    private static SingleMasterConfigurationJsonMarshaller instance;

    SingleMasterConfigurationJsonMarshaller() {
    }

    public void marshall(SingleMasterConfiguration singleMasterConfiguration, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (singleMasterConfiguration.getMessageTtlSeconds() != null) {
            Integer messageTtlSeconds = singleMasterConfiguration.getMessageTtlSeconds();
            jsonWriter.name("MessageTtlSeconds");
            jsonWriter.value((Number) messageTtlSeconds);
        }
        jsonWriter.endObject();
    }

    public static SingleMasterConfigurationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SingleMasterConfigurationJsonMarshaller();
        }
        return instance;
    }
}
