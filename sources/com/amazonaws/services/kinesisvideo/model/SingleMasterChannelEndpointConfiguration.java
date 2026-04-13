package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SingleMasterChannelEndpointConfiguration implements Serializable {
    private List<String> protocols = new ArrayList();
    private String role;

    public List<String> getProtocols() {
        return this.protocols;
    }

    public void setProtocols(Collection<String> protocols2) {
        if (protocols2 == null) {
            this.protocols = null;
        } else {
            this.protocols = new ArrayList(protocols2);
        }
    }

    public SingleMasterChannelEndpointConfiguration withProtocols(String... protocols2) {
        if (getProtocols() == null) {
            this.protocols = new ArrayList(protocols2.length);
        }
        for (String value : protocols2) {
            this.protocols.add(value);
        }
        return this;
    }

    public SingleMasterChannelEndpointConfiguration withProtocols(Collection<String> protocols2) {
        setProtocols(protocols2);
        return this;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role2) {
        this.role = role2;
    }

    public SingleMasterChannelEndpointConfiguration withRole(String role2) {
        this.role = role2;
        return this;
    }

    public void setRole(ChannelRole role2) {
        this.role = role2.toString();
    }

    public SingleMasterChannelEndpointConfiguration withRole(ChannelRole role2) {
        this.role = role2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getProtocols() != null) {
            sb.append("Protocols: " + getProtocols() + ",");
        }
        if (getRole() != null) {
            sb.append("Role: " + getRole());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getProtocols() == null ? 0 : getProtocols().hashCode())) * 31;
        if (getRole() != null) {
            i = getRole().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SingleMasterChannelEndpointConfiguration)) {
            return false;
        }
        SingleMasterChannelEndpointConfiguration other = (SingleMasterChannelEndpointConfiguration) obj;
        if ((other.getProtocols() == null) ^ (getProtocols() == null)) {
            return false;
        }
        if (other.getProtocols() != null && !other.getProtocols().equals(getProtocols())) {
            return false;
        }
        if ((other.getRole() == null) ^ (getRole() == null)) {
            return false;
        }
        if (other.getRole() == null || other.getRole().equals(getRole())) {
            return true;
        }
        return false;
    }
}
