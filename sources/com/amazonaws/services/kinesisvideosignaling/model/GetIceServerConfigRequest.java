package com.amazonaws.services.kinesisvideosignaling.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class GetIceServerConfigRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelARN;
    private String clientId;
    private String service;
    private String username;

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public GetIceServerConfigRequest withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId2) {
        this.clientId = clientId2;
    }

    public GetIceServerConfigRequest withClientId(String clientId2) {
        this.clientId = clientId2;
        return this;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service2) {
        this.service = service2;
    }

    public GetIceServerConfigRequest withService(String service2) {
        this.service = service2;
        return this;
    }

    public void setService(Service service2) {
        this.service = service2.toString();
    }

    public GetIceServerConfigRequest withService(Service service2) {
        this.service = service2.toString();
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public GetIceServerConfigRequest withUsername(String username2) {
        this.username = username2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN() + ",");
        }
        if (getClientId() != null) {
            sb.append("ClientId: " + getClientId() + ",");
        }
        if (getService() != null) {
            sb.append("Service: " + getService() + ",");
        }
        if (getUsername() != null) {
            sb.append("Username: " + getUsername());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode())) * 31) + (getClientId() == null ? 0 : getClientId().hashCode())) * 31) + (getService() == null ? 0 : getService().hashCode())) * 31;
        if (getUsername() != null) {
            i = getUsername().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetIceServerConfigRequest)) {
            return false;
        }
        GetIceServerConfigRequest other = (GetIceServerConfigRequest) obj;
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() != null && !other.getChannelARN().equals(getChannelARN())) {
            return false;
        }
        if ((other.getClientId() == null) ^ (getClientId() == null)) {
            return false;
        }
        if (other.getClientId() != null && !other.getClientId().equals(getClientId())) {
            return false;
        }
        if ((other.getService() == null) ^ (getService() == null)) {
            return false;
        }
        if (other.getService() != null && !other.getService().equals(getService())) {
            return false;
        }
        if ((other.getUsername() == null) ^ (getUsername() == null)) {
            return false;
        }
        if (other.getUsername() == null || other.getUsername().equals(getUsername())) {
            return true;
        }
        return false;
    }
}
