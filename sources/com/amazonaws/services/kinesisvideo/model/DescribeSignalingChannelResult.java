package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class DescribeSignalingChannelResult implements Serializable {
    private ChannelInfo channelInfo;

    public ChannelInfo getChannelInfo() {
        return this.channelInfo;
    }

    public void setChannelInfo(ChannelInfo channelInfo2) {
        this.channelInfo = channelInfo2;
    }

    public DescribeSignalingChannelResult withChannelInfo(ChannelInfo channelInfo2) {
        this.channelInfo = channelInfo2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelInfo() != null) {
            sb.append("ChannelInfo: " + getChannelInfo());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getChannelInfo() == null ? 0 : getChannelInfo().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeSignalingChannelResult)) {
            return false;
        }
        DescribeSignalingChannelResult other = (DescribeSignalingChannelResult) obj;
        if ((other.getChannelInfo() == null) ^ (getChannelInfo() == null)) {
            return false;
        }
        if (other.getChannelInfo() == null || other.getChannelInfo().equals(getChannelInfo())) {
            return true;
        }
        return false;
    }
}
