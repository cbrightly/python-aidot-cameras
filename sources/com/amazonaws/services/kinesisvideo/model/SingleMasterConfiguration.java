package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class SingleMasterConfiguration implements Serializable {
    private Integer messageTtlSeconds;

    public Integer getMessageTtlSeconds() {
        return this.messageTtlSeconds;
    }

    public void setMessageTtlSeconds(Integer messageTtlSeconds2) {
        this.messageTtlSeconds = messageTtlSeconds2;
    }

    public SingleMasterConfiguration withMessageTtlSeconds(Integer messageTtlSeconds2) {
        this.messageTtlSeconds = messageTtlSeconds2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMessageTtlSeconds() != null) {
            sb.append("MessageTtlSeconds: " + getMessageTtlSeconds());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getMessageTtlSeconds() == null ? 0 : getMessageTtlSeconds().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SingleMasterConfiguration)) {
            return false;
        }
        SingleMasterConfiguration other = (SingleMasterConfiguration) obj;
        if ((other.getMessageTtlSeconds() == null) ^ (getMessageTtlSeconds() == null)) {
            return false;
        }
        if (other.getMessageTtlSeconds() == null || other.getMessageTtlSeconds().equals(getMessageTtlSeconds())) {
            return true;
        }
        return false;
    }
}
