package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.SingleMasterChannelEndpointConfiguration;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class SingleMasterChannelEndpointConfigurationJsonUnmarshaller implements Unmarshaller<SingleMasterChannelEndpointConfiguration, JsonUnmarshallerContext> {
    private static SingleMasterChannelEndpointConfigurationJsonUnmarshaller instance;

    SingleMasterChannelEndpointConfigurationJsonUnmarshaller() {
    }

    public SingleMasterChannelEndpointConfiguration unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SingleMasterChannelEndpointConfiguration singleMasterChannelEndpointConfiguration = new SingleMasterChannelEndpointConfiguration();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Protocols")) {
                singleMasterChannelEndpointConfiguration.setProtocols(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Role")) {
                singleMasterChannelEndpointConfiguration.setRole(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return singleMasterChannelEndpointConfiguration;
    }

    public static SingleMasterChannelEndpointConfigurationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SingleMasterChannelEndpointConfigurationJsonUnmarshaller();
        }
        return instance;
    }
}
