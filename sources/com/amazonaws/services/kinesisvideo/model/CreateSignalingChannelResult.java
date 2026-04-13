package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class CreateSignalingChannelResult implements Serializable {
    private String channelARN;

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public CreateSignalingChannelResult withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateSignalingChannelResult)) {
            return false;
        }
        CreateSignalingChannelResult other = (CreateSignalingChannelResult) obj;
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() == null || other.getChannelARN().equals(getChannelARN())) {
            return true;
        }
        return false;
    }
}
