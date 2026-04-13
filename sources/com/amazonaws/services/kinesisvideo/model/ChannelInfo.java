package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.Date;

public class ChannelInfo implements Serializable {
    private String channelARN;
    private String channelName;
    private String channelStatus;
    private String channelType;
    private Date creationTime;
    private SingleMasterConfiguration singleMasterConfiguration;
    private String version;

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName2) {
        this.channelName = channelName2;
    }

    public ChannelInfo withChannelName(String channelName2) {
        this.channelName = channelName2;
        return this;
    }

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public ChannelInfo withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType2) {
        this.channelType = channelType2;
    }

    public ChannelInfo withChannelType(String channelType2) {
        this.channelType = channelType2;
        return this;
    }

    public void setChannelType(ChannelType channelType2) {
        this.channelType = channelType2.toString();
    }

    public ChannelInfo withChannelType(ChannelType channelType2) {
        this.channelType = channelType2.toString();
        return this;
    }

    public String getChannelStatus() {
        return this.channelStatus;
    }

    public void setChannelStatus(String channelStatus2) {
        this.channelStatus = channelStatus2;
    }

    public ChannelInfo withChannelStatus(String channelStatus2) {
        this.channelStatus = channelStatus2;
        return this;
    }

    public void setChannelStatus(Status channelStatus2) {
        this.channelStatus = channelStatus2.toString();
    }

    public ChannelInfo withChannelStatus(Status channelStatus2) {
        this.channelStatus = channelStatus2.toString();
        return this;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Date creationTime2) {
        this.creationTime = creationTime2;
    }

    public ChannelInfo withCreationTime(Date creationTime2) {
        this.creationTime = creationTime2;
        return this;
    }

    public SingleMasterConfiguration getSingleMasterConfiguration() {
        return this.singleMasterConfiguration;
    }

    public void setSingleMasterConfiguration(SingleMasterConfiguration singleMasterConfiguration2) {
        this.singleMasterConfiguration = singleMasterConfiguration2;
    }

    public ChannelInfo withSingleMasterConfiguration(SingleMasterConfiguration singleMasterConfiguration2) {
        this.singleMasterConfiguration = singleMasterConfiguration2;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public ChannelInfo withVersion(String version2) {
        this.version = version2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelName() != null) {
            sb.append("ChannelName: " + getChannelName() + ",");
        }
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN() + ",");
        }
        if (getChannelType() != null) {
            sb.append("ChannelType: " + getChannelType() + ",");
        }
        if (getChannelStatus() != null) {
            sb.append("ChannelStatus: " + getChannelStatus() + ",");
        }
        if (getCreationTime() != null) {
            sb.append("CreationTime: " + getCreationTime() + ",");
        }
        if (getSingleMasterConfiguration() != null) {
            sb.append("SingleMasterConfiguration: " + getSingleMasterConfiguration() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((((((((((1 * 31) + (getChannelName() == null ? 0 : getChannelName().hashCode())) * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode())) * 31) + (getChannelType() == null ? 0 : getChannelType().hashCode())) * 31) + (getChannelStatus() == null ? 0 : getChannelStatus().hashCode())) * 31) + (getCreationTime() == null ? 0 : getCreationTime().hashCode())) * 31;
        if (getSingleMasterConfiguration() == null) {
            i = 0;
        } else {
            i = getSingleMasterConfiguration().hashCode();
        }
        int hashCode2 = (hashCode + i) * 31;
        if (getVersion() != null) {
            i2 = getVersion().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ChannelInfo)) {
            return false;
        }
        ChannelInfo other = (ChannelInfo) obj;
        if ((other.getChannelName() == null) ^ (getChannelName() == null)) {
            return false;
        }
        if (other.getChannelName() != null && !other.getChannelName().equals(getChannelName())) {
            return false;
        }
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() != null && !other.getChannelARN().equals(getChannelARN())) {
            return false;
        }
        if ((other.getChannelType() == null) ^ (getChannelType() == null)) {
            return false;
        }
        if (other.getChannelType() != null && !other.getChannelType().equals(getChannelType())) {
            return false;
        }
        if ((other.getChannelStatus() == null) ^ (getChannelStatus() == null)) {
            return false;
        }
        if (other.getChannelStatus() != null && !other.getChannelStatus().equals(getChannelStatus())) {
            return false;
        }
        if ((other.getCreationTime() == null) ^ (getCreationTime() == null)) {
            return false;
        }
        if (other.getCreationTime() != null && !other.getCreationTime().equals(getCreationTime())) {
            return false;
        }
        if ((other.getSingleMasterConfiguration() == null) ^ (getSingleMasterConfiguration() == null)) {
            return false;
        }
        if (other.getSingleMasterConfiguration() != null && !other.getSingleMasterConfiguration().equals(getSingleMasterConfiguration())) {
            return false;
        }
        if ((other.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        if (other.getVersion() == null || other.getVersion().equals(getVersion())) {
            return true;
        }
        return false;
    }
}
