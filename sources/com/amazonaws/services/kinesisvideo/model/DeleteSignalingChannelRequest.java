package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DeleteSignalingChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelARN;
    private String currentVersion;

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public DeleteSignalingChannelRequest withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
    }

    public DeleteSignalingChannelRequest withCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN() + ",");
        }
        if (getCurrentVersion() != null) {
            sb.append("CurrentVersion: " + getCurrentVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode())) * 31;
        if (getCurrentVersion() != null) {
            i = getCurrentVersion().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteSignalingChannelRequest)) {
            return false;
        }
        DeleteSignalingChannelRequest other = (DeleteSignalingChannelRequest) obj;
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() != null && !other.getChannelARN().equals(getChannelARN())) {
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
