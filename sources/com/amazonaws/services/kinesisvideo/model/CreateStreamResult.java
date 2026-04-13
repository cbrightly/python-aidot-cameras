package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class CreateStreamResult implements Serializable {
    private String streamARN;

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public CreateStreamResult withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateStreamResult)) {
            return false;
        }
        CreateStreamResult other = (CreateStreamResult) obj;
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() == null || other.getStreamARN().equals(getStreamARN())) {
            return true;
        }
        return false;
    }
}
