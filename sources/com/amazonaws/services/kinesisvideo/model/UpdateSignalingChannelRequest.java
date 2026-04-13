package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class UpdateSignalingChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelARN;
    private String currentVersion;
    private SingleMasterConfiguration singleMasterConfiguration;

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public UpdateSignalingChannelRequest withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
    }

    public UpdateSignalingChannelRequest withCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
        return this;
    }

    public SingleMasterConfiguration getSingleMasterConfiguration() {
        return this.singleMasterConfiguration;
    }

    public void setSingleMasterConfiguration(SingleMasterConfiguration singleMasterConfiguration2) {
        this.singleMasterConfiguration = singleMasterConfiguration2;
    }

    public UpdateSignalingChannelRequest withSingleMasterConfiguration(SingleMasterConfiguration singleMasterConfiguration2) {
        this.singleMasterConfiguration = singleMasterConfiguration2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN() + ",");
        }
        if (getCurrentVersion() != null) {
            sb.append("CurrentVersion: " + getCurrentVersion() + ",");
        }
        if (getSingleMasterConfiguration() != null) {
            sb.append("SingleMasterConfiguration: " + getSingleMasterConfiguration());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode())) * 31) + (getCurrentVersion() == null ? 0 : getCurrentVersion().hashCode())) * 31;
        if (getSingleMasterConfiguration() != null) {
            i = getSingleMasterConfiguration().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateSignalingChannelRequest)) {
            return false;
        }
        UpdateSignalingChannelRequest other = (UpdateSignalingChannelRequest) obj;
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() != null && !other.getChannelARN().equals(getChannelARN())) {
            return false;
        }
        if ((other.getCurrentVersion() == null) ^ (getCurrentVersion() == null)) {
            return false;
        }
        if (other.getCurrentVersion() != null && !other.getCurrentVersion().equals(getCurrentVersion())) {
            return false;
        }
        if ((other.getSingleMasterConfiguration() == null) ^ (getSingleMasterConfiguration() == null)) {
            return false;
        }
        if (other.getSingleMasterConfiguration() == null || other.getSingleMasterConfiguration().equals(getSingleMasterConfiguration())) {
            return true;
        }
        return false;
    }
}
