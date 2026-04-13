package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class ResourceEndpointListItem implements Serializable {
    private String protocol;
    private String resourceEndpoint;

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol2) {
        this.protocol = protocol2;
    }

    public ResourceEndpointListItem withProtocol(String protocol2) {
        this.protocol = protocol2;
        return this;
    }

    public void setProtocol(ChannelProtocol protocol2) {
        this.protocol = protocol2.toString();
    }

    public ResourceEndpointListItem withProtocol(ChannelProtocol protocol2) {
        this.protocol = protocol2.toString();
        return this;
    }

    public String getResourceEndpoint() {
        return this.resourceEndpoint;
    }

    public void setResourceEndpoint(String resourceEndpoint2) {
        this.resourceEndpoint = resourceEndpoint2;
    }

    public ResourceEndpointListItem withResourceEndpoint(String resourceEndpoint2) {
        this.resourceEndpoint = resourceEndpoint2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getProtocol() != null) {
            sb.append("Protocol: " + getProtocol() + ",");
        }
        if (getResourceEndpoint() != null) {
            sb.append("ResourceEndpoint: " + getResourceEndpoint());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getProtocol() == null ? 0 : getProtocol().hashCode())) * 31;
        if (getResourceEndpoint() != null) {
            i = getResourceEndpoint().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ResourceEndpointListItem)) {
            return false;
        }
        ResourceEndpointListItem other = (ResourceEndpointListItem) obj;
        if ((other.getProtocol() == null) ^ (getProtocol() == null)) {
            return false;
        }
        if (other.getProtocol() != null && !other.getProtocol().equals(getProtocol())) {
            return false;
        }
        if ((other.getResourceEndpoint() == null) ^ (getResourceEndpoint() == null)) {
            return false;
        }
        if (other.getResourceEndpoint() == null || other.getResourceEndpoint().equals(getResourceEndpoint())) {
            return true;
        }
        return false;
    }
}
