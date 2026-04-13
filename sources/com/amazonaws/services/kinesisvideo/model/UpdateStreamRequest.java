package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class UpdateStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String currentVersion;
    private String deviceName;
    private String mediaType;
    private String streamARN;
    private String streamName;

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public UpdateStreamRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public UpdateStreamRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
    }

    public UpdateStreamRequest withCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
        return this;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public UpdateStreamRequest withDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
        return this;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(String mediaType2) {
        this.mediaType = mediaType2;
    }

    public UpdateStreamRequest withMediaType(String mediaType2) {
        this.mediaType = mediaType2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamName() != null) {
            sb.append("StreamName: " + getStreamName() + ",");
        }
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN() + ",");
        }
        if (getCurrentVersion() != null) {
            sb.append("CurrentVersion: " + getCurrentVersion() + ",");
        }
        if (getDeviceName() != null) {
            sb.append("DeviceName: " + getDeviceName() + ",");
        }
        if (getMediaType() != null) {
            sb.append("MediaType: " + getMediaType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((1 * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31) + (getCurrentVersion() == null ? 0 : getCurrentVersion().hashCode())) * 31) + (getDeviceName() == null ? 0 : getDeviceName().hashCode())) * 31;
        if (getMediaType() != null) {
            i = getMediaType().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateStreamRequest)) {
            return false;
        }
        UpdateStreamRequest other = (UpdateStreamRequest) obj;
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
        if ((other.getCurrentVersion() == null) ^ (getCurrentVersion() == null)) {
            return false;
        }
        if (other.getCurrentVersion() != null && !other.getCurrentVersion().equals(getCurrentVersion())) {
            return false;
        }
        if ((other.getDeviceName() == null) ^ (getDeviceName() == null)) {
            return false;
        }
        if (other.getDeviceName() != null && !other.getDeviceName().equals(getDeviceName())) {
            return false;
        }
        if ((other.getMediaType() == null) ^ (getMediaType() == null)) {
            return false;
        }
        if (other.getMediaType() == null || other.getMediaType().equals(getMediaType())) {
            return true;
        }
        return false;
    }
}
