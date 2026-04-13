package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.kinesisvideo.model.ChannelInfo;
import com.amazonaws.services.kinesisvideo.model.SingleMasterConfiguration;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Date;

public class ChannelInfoJsonMarshaller {
    private static ChannelInfoJsonMarshaller instance;

    ChannelInfoJsonMarshaller() {
    }

    public void marshall(ChannelInfo channelInfo, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (channelInfo.getChannelName() != null) {
            String channelName = channelInfo.getChannelName();
            jsonWriter.name("ChannelName");
            jsonWriter.value(channelName);
        }
        if (channelInfo.getChannelARN() != null) {
            String channelARN = channelInfo.getChannelARN();
            jsonWriter.name("ChannelARN");
            jsonWriter.value(channelARN);
        }
        if (channelInfo.getChannelType() != null) {
            String channelType = channelInfo.getChannelType();
            jsonWriter.name("ChannelType");
            jsonWriter.value(channelType);
        }
        if (channelInfo.getChannelStatus() != null) {
            String channelStatus = channelInfo.getChannelStatus();
            jsonWriter.name("ChannelStatus");
            jsonWriter.value(channelStatus);
        }
        if (channelInfo.getCreationTime() != null) {
            Date creationTime = channelInfo.getCreationTime();
            jsonWriter.name("CreationTime");
            jsonWriter.value(creationTime);
        }
        if (channelInfo.getSingleMasterConfiguration() != null) {
            SingleMasterConfiguration singleMasterConfiguration = channelInfo.getSingleMasterConfiguration();
            jsonWriter.name("SingleMasterConfiguration");
            SingleMasterConfigurationJsonMarshaller.getInstance().marshall(singleMasterConfiguration, jsonWriter);
        }
        if (channelInfo.getVersion() != null) {
            String version = channelInfo.getVersion();
            jsonWriter.name(JsonDocumentFields.VERSION);
            jsonWriter.value(version);
        }
        jsonWriter.endObject();
    }

    public static ChannelInfoJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelInfoJsonMarshaller();
        }
        return instance;
    }
}
