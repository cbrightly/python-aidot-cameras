package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DescribeSignalingChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelARN;
    private String channelName;

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName2) {
        this.channelName = channelName2;
    }

    public DescribeSignalingChannelRequest withChannelName(String channelName2) {
        this.channelName = channelName2;
        return this;
    }

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public DescribeSignalingChannelRequest withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelName() != null) {
            sb.append("ChannelName: " + getChannelName() + ",");
        }
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getChannelName() == null ? 0 : getChannelName().hashCode())) * 31;
        if (getChannelARN() != null) {
            i = getChannelARN().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeSignalingChannelRequest)) {
            return false;
        }
        DescribeSignalingChannelRequest other = (DescribeSignalingChannelRequest) obj;
        if ((other.getChannelName() == null) ^ (getChannelName() == null)) {
            return false;
        }
        if (other.getChannelName() != null && !other.getChannelName().equals(getChannelName())) {
            return false;
        }
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() == null || other.getChannelARN().equals(getChannelARN())) {
            return true;
        }
        return false;
    }
}
