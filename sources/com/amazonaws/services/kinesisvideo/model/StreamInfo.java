package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.Date;

public class StreamInfo implements Serializable {
    private Date creationTime;
    private Integer dataRetentionInHours;
    private String deviceName;
    private String kmsKeyId;
    private String mediaType;
    private String status;
    private String streamARN;
    private String streamName;
    private String version;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public StreamInfo withDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
        return this;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public StreamInfo withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public StreamInfo withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(String mediaType2) {
        this.mediaType = mediaType2;
    }

    public StreamInfo withMediaType(String mediaType2) {
        this.mediaType = mediaType2;
        return this;
    }

    public String getKmsKeyId() {
        return this.kmsKeyId;
    }

    public void setKmsKeyId(String kmsKeyId2) {
        this.kmsKeyId = kmsKeyId2;
    }

    public StreamInfo withKmsKeyId(String kmsKeyId2) {
        this.kmsKeyId = kmsKeyId2;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public StreamInfo withVersion(String version2) {
        this.version = version2;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public StreamInfo withStatus(String status2) {
        this.status = status2;
        return this;
    }

    public void setStatus(Status status2) {
        this.status = status2.toString();
    }

    public StreamInfo withStatus(Status status2) {
        this.status = status2.toString();
        return this;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Date creationTime2) {
        this.creationTime = creationTime2;
    }

    public StreamInfo withCreationTime(Date creationTime2) {
        this.creationTime = creationTime2;
        return this;
    }

    public Integer getDataRetentionInHours() {
        return this.dataRetentionInHours;
    }

    public void setDataRetentionInHours(Integer dataRetentionInHours2) {
        this.dataRetentionInHours = dataRetentionInHours2;
    }

    public StreamInfo withDataRetentionInHours(Integer dataRetentionInHours2) {
        this.dataRetentionInHours = dataRetentionInHours2;
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
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN() + ",");
        }
        if (getMediaType() != null) {
            sb.append("MediaType: " + getMediaType() + ",");
        }
        if (getKmsKeyId() != null) {
            sb.append("KmsKeyId: " + getKmsKeyId() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion() + ",");
        }
        if (getStatus() != null) {
            sb.append("Status: " + getStatus() + ",");
        }
        if (getCreationTime() != null) {
            sb.append("CreationTime: " + getCreationTime() + ",");
        }
        if (getDataRetentionInHours() != null) {
            sb.append("DataRetentionInHours: " + getDataRetentionInHours());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((1 * 31) + (getDeviceName() == null ? 0 : getDeviceName().hashCode())) * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31) + (getMediaType() == null ? 0 : getMediaType().hashCode())) * 31) + (getKmsKeyId() == null ? 0 : getKmsKeyId().hashCode())) * 31) + (getVersion() == null ? 0 : getVersion().hashCode())) * 31) + (getStatus() == null ? 0 : getStatus().hashCode())) * 31) + (getCreationTime() == null ? 0 : getCreationTime().hashCode())) * 31;
        if (getDataRetentionInHours() != null) {
            i = getDataRetentionInHours().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof StreamInfo)) {
            return false;
        }
        StreamInfo other = (StreamInfo) obj;
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
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() != null && !other.getStreamARN().equals(getStreamARN())) {
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
        if ((other.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        if (other.getVersion() != null && !other.getVersion().equals(getVersion())) {
            return false;
        }
        if ((other.getStatus() == null) ^ (getStatus() == null)) {
            return false;
        }
        if (other.getStatus() != null && !other.getStatus().equals(getStatus())) {
            return false;
        }
        if ((other.getCreationTime() == null) ^ (getCreationTime() == null)) {
            return false;
        }
        if (other.getCreationTime() != null && !other.getCreationTime().equals(getCreationTime())) {
            return false;
        }
        if ((other.getDataRetentionInHours() == null) ^ (getDataRetentionInHours() == null)) {
            return false;
        }
        if (other.getDataRetentionInHours() == null || other.getDataRetentionInHours().equals(getDataRetentionInHours())) {
            return true;
        }
        return false;
    }
}
