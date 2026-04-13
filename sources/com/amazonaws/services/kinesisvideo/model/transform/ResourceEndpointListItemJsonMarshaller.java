package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem;
import com.amazonaws.util.json.AwsJsonWriter;

public class ResourceEndpointListItemJsonMarshaller {
    private static ResourceEndpointListItemJsonMarshaller instance;

    ResourceEndpointListItemJsonMarshaller() {
    }

    public void marshall(ResourceEndpointListItem resourceEndpointListItem, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (resourceEndpointListItem.getProtocol() != null) {
            String protocol = resourceEndpointListItem.getProtocol();
            jsonWriter.name("Protocol");
            jsonWriter.value(protocol);
        }
        if (resourceEndpointListItem.getResourceEndpoint() != null) {
            String resourceEndpoint = resourceEndpointListItem.getResourceEndpoint();
            jsonWriter.name("ResourceEndpoint");
            jsonWriter.value(resourceEndpoint);
        }
        jsonWriter.endObject();
    }

    public static ResourceEndpointListItemJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ResourceEndpointListItemJsonMarshaller();
        }
        return instance;
    }
}
