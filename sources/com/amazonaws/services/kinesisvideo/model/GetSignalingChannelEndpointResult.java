package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetSignalingChannelEndpointResult implements Serializable {
    private List<ResourceEndpointListItem> resourceEndpointList = new ArrayList();

    public List<ResourceEndpointListItem> getResourceEndpointList() {
        return this.resourceEndpointList;
    }

    public void setResourceEndpointList(Collection<ResourceEndpointListItem> resourceEndpointList2) {
        if (resourceEndpointList2 == null) {
            this.resourceEndpointList = null;
        } else {
            this.resourceEndpointList = new ArrayList(resourceEndpointList2);
        }
    }

    public GetSignalingChannelEndpointResult withResourceEndpointList(ResourceEndpointListItem... resourceEndpointList2) {
        if (getResourceEndpointList() == null) {
            this.resourceEndpointList = new ArrayList(resourceEndpointList2.length);
        }
        for (ResourceEndpointListItem value : resourceEndpointList2) {
            this.resourceEndpointList.add(value);
        }
        return this;
    }

    public GetSignalingChannelEndpointResult withResourceEndpointList(Collection<ResourceEndpointListItem> resourceEndpointList2) {
        setResourceEndpointList(resourceEndpointList2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResourceEndpointList() != null) {
            sb.append("ResourceEndpointList: " + getResourceEndpointList());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getResourceEndpointList() == null ? 0 : getResourceEndpointList().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSignalingChannelEndpointResult)) {
            return false;
        }
        GetSignalingChannelEndpointResult other = (GetSignalingChannelEndpointResult) obj;
        if ((other.getResourceEndpointList() == null) ^ (getResourceEndpointList() == null)) {
            return false;
        }
        if (other.getResourceEndpointList() == null || other.getResourceEndpointList().equals(getResourceEndpointList())) {
            return true;
        }
        return false;
    }
}
