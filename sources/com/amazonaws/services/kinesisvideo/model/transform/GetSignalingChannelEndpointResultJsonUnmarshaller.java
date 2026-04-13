package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetSignalingChannelEndpointResultJsonUnmarshaller implements Unmarshaller<GetSignalingChannelEndpointResult, JsonUnmarshallerContext> {
    private static GetSignalingChannelEndpointResultJsonUnmarshaller instance;

    public GetSignalingChannelEndpointResult unmarshall(JsonUnmarshallerContext context) {
        GetSignalingChannelEndpointResult getSignalingChannelEndpointResult = new GetSignalingChannelEndpointResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("ResourceEndpointList")) {
                getSignalingChannelEndpointResult.setResourceEndpointList(new ListUnmarshaller(ResourceEndpointListItemJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getSignalingChannelEndpointResult;
    }

    public static GetSignalingChannelEndpointResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSignalingChannelEndpointResultJsonUnmarshaller();
        }
        return instance;
    }
}
