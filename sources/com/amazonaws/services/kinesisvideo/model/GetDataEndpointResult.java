package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class GetDataEndpointResult implements Serializable {
    private String dataEndpoint;

    public String getDataEndpoint() {
        return this.dataEndpoint;
    }

    public void setDataEndpoint(String dataEndpoint2) {
        this.dataEndpoint = dataEndpoint2;
    }

    public GetDataEndpointResult withDataEndpoint(String dataEndpoint2) {
        this.dataEndpoint = dataEndpoint2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDataEndpoint() != null) {
            sb.append("DataEndpoint: " + getDataEndpoint());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getDataEndpoint() == null ? 0 : getDataEndpoint().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetDataEndpointResult)) {
            return false;
        }
        GetDataEndpointResult other = (GetDataEndpointResult) obj;
        if ((other.getDataEndpoint() == null) ^ (getDataEndpoint() == null)) {
            return false;
        }
        if (other.getDataEndpoint() == null || other.getDataEndpoint().equals(getDataEndpoint())) {
            return true;
        }
        return false;
    }
}
