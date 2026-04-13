package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.kinesisvideo.model.ChannelInfo;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ChannelInfoJsonUnmarshaller implements Unmarshaller<ChannelInfo, JsonUnmarshallerContext> {
    private static ChannelInfoJsonUnmarshaller instance;

    ChannelInfoJsonUnmarshaller() {
    }

    public ChannelInfo unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ChannelInfo channelInfo = new ChannelInfo();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ChannelName")) {
                channelInfo.setChannelName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ChannelARN")) {
                channelInfo.setChannelARN(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ChannelType")) {
                channelInfo.setChannelType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ChannelStatus")) {
                channelInfo.setChannelStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("CreationTime")) {
                channelInfo.setCreationTime(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SingleMasterConfiguration")) {
                channelInfo.setSingleMasterConfiguration(SingleMasterConfigurationJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals(JsonDocumentFields.VERSION)) {
                channelInfo.setVersion(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return channelInfo;
    }

    public static ChannelInfoJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelInfoJsonUnmarshaller();
        }
        return instance;
    }
}
