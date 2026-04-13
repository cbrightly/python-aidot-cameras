package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.SingleMasterChannelEndpointConfiguration;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

public class SingleMasterChannelEndpointConfigurationJsonMarshaller {
    private static SingleMasterChannelEndpointConfigurationJsonMarshaller instance;

    SingleMasterChannelEndpointConfigurationJsonMarshaller() {
    }

    public void marshall(SingleMasterChannelEndpointConfiguration singleMasterChannelEndpointConfiguration, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (singleMasterChannelEndpointConfiguration.getProtocols() != null) {
            List<String> protocols = singleMasterChannelEndpointConfiguration.getProtocols();
            jsonWriter.name("Protocols");
            jsonWriter.beginArray();
            for (String protocolsItem : protocols) {
                if (protocolsItem != null) {
                    jsonWriter.value(protocolsItem);
                }
            }
            jsonWriter.endArray();
        }
        if (singleMasterChannelEndpointConfiguration.getRole() != null) {
            String role = singleMasterChannelEndpointConfiguration.getRole();
            jsonWriter.name("Role");
            jsonWriter.value(role);
        }
        jsonWriter.endObject();
    }

    public static SingleMasterChannelEndpointConfigurationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SingleMasterChannelEndpointConfigurationJsonMarshaller();
        }
        return instance;
    }
}
