package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.services.kinesisvideosignaling.model.IceServer;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class IceServerJsonUnmarshaller implements Unmarshaller<IceServer, JsonUnmarshallerContext> {
    private static IceServerJsonUnmarshaller instance;

    IceServerJsonUnmarshaller() {
    }

    public IceServer unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        IceServer iceServer = new IceServer();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Uris")) {
                iceServer.setUris(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Username")) {
                iceServer.setUsername(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Password")) {
                iceServer.setPassword(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Ttl")) {
                iceServer.setTtl(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return iceServer;
    }

    public static IceServerJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new IceServerJsonUnmarshaller();
        }
        return instance;
    }
}
