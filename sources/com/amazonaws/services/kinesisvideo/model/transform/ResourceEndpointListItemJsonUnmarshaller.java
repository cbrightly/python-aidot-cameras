package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ResourceEndpointListItemJsonUnmarshaller implements Unmarshaller<ResourceEndpointListItem, JsonUnmarshallerContext> {
    private static ResourceEndpointListItemJsonUnmarshaller instance;

    ResourceEndpointListItemJsonUnmarshaller() {
    }

    public ResourceEndpointListItem unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ResourceEndpointListItem resourceEndpointListItem = new ResourceEndpointListItem();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Protocol")) {
                resourceEndpointListItem.setProtocol(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ResourceEndpoint")) {
                resourceEndpointListItem.setResourceEndpoint(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return resourceEndpointListItem;
    }

    public static ResourceEndpointListItemJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ResourceEndpointListItemJsonUnmarshaller();
        }
        return instance;
    }
}
