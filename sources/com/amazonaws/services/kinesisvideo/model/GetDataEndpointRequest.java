package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class GetDataEndpointRequest extends AmazonWebServiceRequest implements Serializable {
    private String aPIName;
    private String streamARN;
    private String streamName;

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public GetDataEndpointRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public GetDataEndpointRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getAPIName() {
        return this.aPIName;
    }

    public void setAPIName(String aPIName2) {
        this.aPIName = aPIName2;
    }

    public GetDataEndpointRequest withAPIName(String aPIName2) {
        this.aPIName = aPIName2;
        return this;
    }

    public void setAPIName(APIName aPIName2) {
        this.aPIName = aPIName2.toString();
    }

    public GetDataEndpointRequest withAPIName(APIName aPIName2) {
        this.aPIName = aPIName2.toString();
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
        if (getAPIName() != null) {
            sb.append("APIName: " + getAPIName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31;
        if (getAPIName() != null) {
            i = getAPIName().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetDataEndpointRequest)) {
            return false;
        }
        GetDataEndpointRequest other = (GetDataEndpointRequest) obj;
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
        if ((other.getAPIName() == null) ^ (getAPIName() == null)) {
            return false;
        }
        if (other.getAPIName() == null || other.getAPIName().equals(getAPIName())) {
            return true;
        }
        return false;
    }
}
