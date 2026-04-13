package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.kinesisvideo.model.StreamInfo;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Date;

public class StreamInfoJsonMarshaller {
    private static StreamInfoJsonMarshaller instance;

    StreamInfoJsonMarshaller() {
    }

    public void marshall(StreamInfo streamInfo, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (streamInfo.getDeviceName() != null) {
            String deviceName = streamInfo.getDeviceName();
            jsonWriter.name("DeviceName");
            jsonWriter.value(deviceName);
        }
        if (streamInfo.getStreamName() != null) {
            String streamName = streamInfo.getStreamName();
            jsonWriter.name("StreamName");
            jsonWriter.value(streamName);
        }
        if (streamInfo.getStreamARN() != null) {
            String streamARN = streamInfo.getStreamARN();
            jsonWriter.name("StreamARN");
            jsonWriter.value(streamARN);
        }
        if (streamInfo.getMediaType() != null) {
            String mediaType = streamInfo.getMediaType();
            jsonWriter.name("MediaType");
            jsonWriter.value(mediaType);
        }
        if (streamInfo.getKmsKeyId() != null) {
            String kmsKeyId = streamInfo.getKmsKeyId();
            jsonWriter.name("KmsKeyId");
            jsonWriter.value(kmsKeyId);
        }
        if (streamInfo.getVersion() != null) {
            String version = streamInfo.getVersion();
            jsonWriter.name(JsonDocumentFields.VERSION);
            jsonWriter.value(version);
        }
        if (streamInfo.getStatus() != null) {
            String status = streamInfo.getStatus();
            jsonWriter.name("Status");
            jsonWriter.value(status);
        }
        if (streamInfo.getCreationTime() != null) {
            Date creationTime = streamInfo.getCreationTime();
            jsonWriter.name("CreationTime");
            jsonWriter.value(creationTime);
        }
        if (streamInfo.getDataRetentionInHours() != null) {
            Integer dataRetentionInHours = streamInfo.getDataRetentionInHours();
            jsonWriter.name("DataRetentionInHours");
            jsonWriter.value((Number) dataRetentionInHours);
        }
        jsonWriter.endObject();
    }

    public static StreamInfoJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new StreamInfoJsonMarshaller();
        }
        return instance;
    }
}
