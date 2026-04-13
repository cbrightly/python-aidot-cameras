package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.kinesisvideo.model.StreamInfo;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class StreamInfoJsonUnmarshaller implements Unmarshaller<StreamInfo, JsonUnmarshallerContext> {
    private static StreamInfoJsonUnmarshaller instance;

    StreamInfoJsonUnmarshaller() {
    }

    public StreamInfo unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        StreamInfo streamInfo = new StreamInfo();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("DeviceName")) {
                streamInfo.setDeviceName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("StreamName")) {
                streamInfo.setStreamName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("StreamARN")) {
                streamInfo.setStreamARN(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("MediaType")) {
                streamInfo.setMediaType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KmsKeyId")) {
                streamInfo.setKmsKeyId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals(JsonDocumentFields.VERSION)) {
                streamInfo.setVersion(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Status")) {
                streamInfo.setStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("CreationTime")) {
                streamInfo.setCreationTime(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("DataRetentionInHours")) {
                streamInfo.setDataRetentionInHours(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return streamInfo;
    }

    public static StreamInfoJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new StreamInfoJsonUnmarshaller();
        }
        return instance;
    }
}
