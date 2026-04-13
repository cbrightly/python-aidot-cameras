package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CreateStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer dataRetentionInHours;
    private String deviceName;
    private String kmsKeyId;
    private String mediaType;
    private String streamName;
    private Map<String, String> tags = new HashMap();

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public CreateStreamRequest withDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
        return this;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public CreateStreamRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(String mediaType2) {
        this.mediaType = mediaType2;
    }

    public CreateStreamRequest withMediaType(String mediaType2) {
        this.mediaType = mediaType2;
        return this;
    }

    public String getKmsKeyId() {
        return this.kmsKeyId;
    }

    public void setKmsKeyId(String kmsKeyId2) {
        this.kmsKeyId = kmsKeyId2;
    }

    public CreateStreamRequest withKmsKeyId(String kmsKeyId2) {
        this.kmsKeyId = kmsKeyId2;
        return this;
    }

    public Integer getDataRetentionInHours() {
        return this.dataRetentionInHours;
    }

    public void setDataRetentionInHours(Integer dataRetentionInHours2) {
        this.dataRetentionInHours = dataRetentionInHours2;
    }

    public CreateStreamRequest withDataRetentionInHours(Integer dataRetentionInHours2) {
        this.dataRetentionInHours = dataRetentionInHours2;
        return this;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags2) {
        this.tags = tags2;
    }

    public CreateStreamRequest withTags(Map<String, String> tags2) {
        this.tags = tags2;
        return this;
    }

    public CreateStreamRequest addTagsEntry(String key, String value) {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        if (!this.tags.containsKey(key)) {
            this.tags.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public CreateStreamRequest clearTagsEntries() {
        this.tags = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDeviceName() != null) {
            sb.append("DeviceName: " + getDeviceName() + ",");
        }
        if (getStreamName() != null) {
            sb.append("StreamName: " + getStreamName() + ",");
        }
        if (getMediaType() != null) {
            sb.append("MediaType: " + getMediaType() + ",");
        }
        if (getKmsKeyId() != null) {
            sb.append("KmsKeyId: " + getKmsKeyId() + ",");
        }
        if (getDataRetentionInHours() != null) {
            sb.append("DataRetentionInHours: " + getDataRetentionInHours() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((1 * 31) + (getDeviceName() == null ? 0 : getDeviceName().hashCode())) * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31) + (getMediaType() == null ? 0 : getMediaType().hashCode())) * 31) + (getKmsKeyId() == null ? 0 : getKmsKeyId().hashCode())) * 31) + (getDataRetentionInHours() == null ? 0 : getDataRetentionInHours().hashCode())) * 31;
        if (getTags() != null) {
            i = getTags().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateStreamRequest)) {
            return false;
        }
        CreateStreamRequest other = (CreateStreamRequest) obj;
        if ((other.getDeviceName() == null) ^ (getDeviceName() == null)) {
            return false;
        }
        if (other.getDeviceName() != null && !other.getDeviceName().equals(getDeviceName())) {
            return false;
        }
        if ((other.getStreamName() == null) ^ (getStreamName() == null)) {
            return false;
        }
        if (other.getStreamName() != null && !other.getStreamName().equals(getStreamName())) {
            return false;
        }
        if ((other.getMediaType() == null) ^ (getMediaType() == null)) {
            return false;
        }
        if (other.getMediaType() != null && !other.getMediaType().equals(getMediaType())) {
            return false;
        }
        if ((other.getKmsKeyId() == null) ^ (getKmsKeyId() == null)) {
            return false;
        }
        if (other.getKmsKeyId() != null && !other.getKmsKeyId().equals(getKmsKeyId())) {
            return false;
        }
        if ((other.getDataRetentionInHours() == null) ^ (getDataRetentionInHours() == null)) {
            return false;
        }
        if (other.getDataRetentionInHours() != null && !other.getDataRetentionInHours().equals(getDataRetentionInHours())) {
            return false;
        }
        if ((other.getTags() == null) ^ (getTags() == null)) {
            return false;
        }
        if (other.getTags() == null || other.getTags().equals(getTags())) {
            return true;
        }
        return false;
    }
}
