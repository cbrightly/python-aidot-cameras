package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.GetDataEndpointResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetDataEndpointResultJsonUnmarshaller implements Unmarshaller<GetDataEndpointResult, JsonUnmarshallerContext> {
    private static GetDataEndpointResultJsonUnmarshaller instance;

    public GetDataEndpointResult unmarshall(JsonUnmarshallerContext context) {
        GetDataEndpointResult getDataEndpointResult = new GetDataEndpointResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("DataEndpoint")) {
                getDataEndpointResult.setDataEndpoint(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getDataEndpointResult;
    }

    public static GetDataEndpointResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetDataEndpointResultJsonUnmarshaller();
        }
        return instance;
    }
}
