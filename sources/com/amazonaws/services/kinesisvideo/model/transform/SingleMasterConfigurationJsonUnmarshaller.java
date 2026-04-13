package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.SingleMasterConfiguration;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class SingleMasterConfigurationJsonUnmarshaller implements Unmarshaller<SingleMasterConfiguration, JsonUnmarshallerContext> {
    private static SingleMasterConfigurationJsonUnmarshaller instance;

    SingleMasterConfigurationJsonUnmarshaller() {
    }

    public SingleMasterConfiguration unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SingleMasterConfiguration singleMasterConfiguration = new SingleMasterConfiguration();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("MessageTtlSeconds")) {
                singleMasterConfiguration.setMessageTtlSeconds(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return singleMasterConfiguration;
    }

    public static SingleMasterConfigurationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SingleMasterConfigurationJsonUnmarshaller();
        }
        return instance;
    }
}
