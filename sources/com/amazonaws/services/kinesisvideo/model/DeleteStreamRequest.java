package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DeleteStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String currentVersion;
    private String streamARN;

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public DeleteStreamRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
    }

    public DeleteStreamRequest withCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN() + ",");
        }
        if (getCurrentVersion() != null) {
            sb.append("CurrentVersion: " + getCurrentVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31;
        if (getCurrentVersion() != null) {
            i = getCurrentVersion().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteStreamRequest)) {
            return false;
        }
        DeleteStreamRequest other = (DeleteStreamRequest) obj;
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() != null && !other.getStreamARN().equals(getStreamARN())) {
            return false;
        }
        if ((other.getCurrentVersion() == null) ^ (getCurrentVersion() == null)) {
            return false;
        }
        if (other.getCurrentVersion() == null || other.getCurrentVersion().equals(getCurrentVersion())) {
            return true;
        }
        return false;
    }
}
