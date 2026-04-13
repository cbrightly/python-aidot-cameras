package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetIceServerConfigResultJsonUnmarshaller implements Unmarshaller<GetIceServerConfigResult, JsonUnmarshallerContext> {
    private static GetIceServerConfigResultJsonUnmarshaller instance;

    public GetIceServerConfigResult unmarshall(JsonUnmarshallerContext context) {
        GetIceServerConfigResult getIceServerConfigResult = new GetIceServerConfigResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("IceServerList")) {
                getIceServerConfigResult.setIceServerList(new ListUnmarshaller(IceServerJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getIceServerConfigResult;
    }

    public static GetIceServerConfigResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetIceServerConfigResultJsonUnmarshaller();
        }
        return instance;
    }
}
