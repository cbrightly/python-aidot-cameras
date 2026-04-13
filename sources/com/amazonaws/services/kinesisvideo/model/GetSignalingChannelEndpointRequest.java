package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class GetSignalingChannelEndpointRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelARN;
    private SingleMasterChannelEndpointConfiguration singleMasterChannelEndpointConfiguration;

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public GetSignalingChannelEndpointRequest withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public SingleMasterChannelEndpointConfiguration getSingleMasterChannelEndpointConfiguration() {
        return this.singleMasterChannelEndpointConfiguration;
    }

    public void setSingleMasterChannelEndpointConfiguration(SingleMasterChannelEndpointConfiguration singleMasterChannelEndpointConfiguration2) {
        this.singleMasterChannelEndpointConfiguration = singleMasterChannelEndpointConfiguration2;
    }

    public GetSignalingChannelEndpointRequest withSingleMasterChannelEndpointConfiguration(SingleMasterChannelEndpointConfiguration singleMasterChannelEndpointConfiguration2) {
        this.singleMasterChannelEndpointConfiguration = singleMasterChannelEndpointConfiguration2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN() + ",");
        }
        if (getSingleMasterChannelEndpointConfiguration() != null) {
            sb.append("SingleMasterChannelEndpointConfiguration: " + getSingleMasterChannelEndpointConfiguration());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode())) * 31;
        if (getSingleMasterChannelEndpointConfiguration() != null) {
            i = getSingleMasterChannelEndpointConfiguration().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSignalingChannelEndpointRequest)) {
            return false;
        }
        GetSignalingChannelEndpointRequest other = (GetSignalingChannelEndpointRequest) obj;
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() != null && !other.getChannelARN().equals(getChannelARN())) {
            return false;
        }
        if ((other.getSingleMasterChannelEndpointConfiguration() == null) ^ (getSingleMasterChannelEndpointConfiguration() == null)) {
            return false;
        }
        if (other.getSingleMasterChannelEndpointConfiguration() == null || other.getSingleMasterChannelEndpointConfiguration().equals(getSingleMasterChannelEndpointConfiguration())) {
            return true;
        }
        return false;
    }
}
